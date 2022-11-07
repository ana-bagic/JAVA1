package hr.fer.zemris.java.fractals;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Program stvara Newton-Raphson fraktal zadavanjem nultočaka.
 * Vizualizacija je ostvarena višedretvenošću.
 * 
 * @author Ana Bagić
 *
 */
public class NewtonParallel {

	public static void main(String[] args) {
		
		if(args.length > 4)
			throw new IllegalArgumentException("Too many arguments.");
		
		int workers = -1;
		int tracks = -1;
		
		for(int i = 0; i < args.length; i++) {
			
			try {
				if(args[i].startsWith("--workers=")) {
					if(workers != -1)
						throw new IllegalArgumentException();
					
					int result = Integer.parseInt(args[i].substring(10));
					if(result < 1)
						throw new IllegalArgumentException();
					
					workers = result;
				} else if(args[i].startsWith("--tracks=")) {
					if(tracks != -1)
						throw new IllegalArgumentException();
					
					int result = Integer.parseInt(args[i].substring(9));
					if(result < 1)
						throw new IllegalArgumentException();
					
					tracks = result;
				} else if(args[i].startsWith("-w")) {
					if(workers != -1 || i == args.length - 1)
						throw new IllegalArgumentException();
					
					i++;
					int result = Integer.parseInt(args[i]);
					if(result < 1)
						throw new IllegalArgumentException();
					
					workers = result;
				} else if(args[i].startsWith("-t")) {
					if(tracks != -1 || i == args.length - 1)
						throw new IllegalArgumentException();
					
					i++;
					int result = Integer.parseInt(args[i]);
					if(result < 1)
						throw new IllegalArgumentException();
					
					tracks = result;
				} else {
					throw new IllegalArgumentException();
				}
				
			} catch(Exception e) {
				throw new IllegalArgumentException("Illegal agrument: " + args[i]);
			}
		}
		
		if(workers == -1)
			workers = Runtime.getRuntime().availableProcessors();
		if(tracks == -1)
			tracks = Runtime.getRuntime().availableProcessors() * 4;
		
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		List<Complex> roots = new LinkedList<>();
		int counter = 1;
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.print("Root " + counter + "> ");
			String nextLine = sc.nextLine().trim();
			
			if(nextLine.equals("done"))
				break;
			
			try {
				roots.add(Util.parseToComplex(nextLine));
			} catch(IllegalArgumentException e) {
				System.out.println(e.getMessage());
				continue;
			}
			
			counter++;
		}
		
		sc.close();
		System.out.println("Image od fractal will appear shortly. Thank you.");
		
		FractalViewer.show(new MyProducer(roots, workers, tracks));
	}
	
	/**
	 * Posao koji obavlja jedna dretva.
	 * 
	 * @author Ana Bagić
	 *
	 */
	public static class Job implements Runnable {
		
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		AtomicBoolean cancel;
		ComplexRootedPolynomial pol;
		/**
		 * Oznaka da nema više poslova.
		 */
		public static Job NO_JOB = new Job();
		
		private Job() {
		}
		
		public Job(double reMin, double reMax, double imMin, double imMax, int width, int height,
				int yMin, int yMax, int m, short[] data, AtomicBoolean cancel, ComplexRootedPolynomial pol) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
			this.pol = pol;
		}
		
		@Override
		public void run() {

			ComplexPolynomial polynomial = pol.toComplexPolynom();
			ComplexPolynomial derived = polynomial.derive();
			
			int offset = width*yMin;
			double convTreshold = 1E-3;
			double rootTreshold = 2E-3;
			for(int y = yMin; y <= yMax; y++) {
				if(cancel.get())
					break;
				
				for(int x = 0; x < width; x++) {
					double zRe = x / (width - 1.0) * (reMax - reMin) + reMin;
					double zIm = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;
					Complex zn = new Complex(zRe, zIm);
					
					double module = 0;
					int iters = 0;
					do {
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						Complex fraction = numerator.divide(denominator);
						Complex znold = zn;
						Complex znSub = zn.sub(fraction);
						zn = znSub;
						module = znold.sub(zn).module();

						iters++;
					} while(iters < m && module > convTreshold);
					int index = pol.indexOfClosestRootFor(zn, rootTreshold);
					data[offset++] = (short) (index + 1);
				}
			}
		}
	}
	
	/**
	 * Razred stvara dretve i poslove koje će obrađivati dretve kako bi se vizualizirao fraktal.
	 * 
	 * @author Ana Bagić
	 *
	 */
	public static class MyProducer implements IFractalProducer {
		
		/**
		 * Polinom korišten za vizualizaciju fraktala.
		 */
		private ComplexRootedPolynomial pol;
		/**
		 * Broj dretvi.
		 */
		private int workers;
		/**
		 * Broj poslova.
		 */
		private int tracks;
		
		public MyProducer(List<Complex> roots, int workers, int tracks) {
			pol = new ComplexRootedPolynomial(Complex.ONE, roots.stream().toArray(Complex[]::new));
			this.workers = workers;
			this.tracks = tracks;
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			
			ComplexPolynomial polynomial = pol.toComplexPolynom();
			int m = 16*16*16;
			short[] data = new short[width * height];
			
			if(tracks > height)
				tracks = height;
			System.out.println("Number of threads: " + workers + ", number of jobs: " + tracks);
			
			int rowsForTrack = height / tracks;
			
			final BlockingQueue<Job> queue = new LinkedBlockingQueue<>();

			Thread[] radnici = new Thread[workers];
			for(int i = 0; i < workers; i++) {
				radnici[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while(true) {
							Job p = null;
							try {
								p = queue.take();
								if(p == Job.NO_JOB)
									break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			
			for(int i = 0; i < workers; i++) {
				radnici[i].start();
			}
			
			for(int i = 0; i < tracks; i++) {
				int yMin = i*rowsForTrack;
				int yMax = (i+1)*rowsForTrack - 1;
				
				if(i == tracks - 1)
					yMax = height - 1;
				
				Job posao = new Job(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel, pol);
				while(true) {
					try {
						queue.put(posao);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			for(int i = 0; i < workers; i++) {
				while(true) {
					try {
						queue.put(Job.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			for(int i = 0; i < workers; i++) {
				while(true) {
					try {
						radnici[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}

			observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
		}
	}
}
