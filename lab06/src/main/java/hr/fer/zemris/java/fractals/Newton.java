package hr.fer.zemris.java.fractals;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Program stvara Newton-Raphson fraktal zadavanjem nultočaka.
 * 
 * @author Ana Bagić
 *
 */
public class Newton {

	public static void main(String[] args) {
		
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
		
		FractalViewer.show(new MyProducer(roots));
	}
	
	/**
	 * Razred generira podatke za vizualizaciju Newton-Raphson fraktala.
	 * 
	 * @author Ana Bagić
	 *
	 */
	public static class MyProducer implements IFractalProducer {
		
		/**
		 * Polinom korišten za vizualizaciju fraktala.
		 */
		private ComplexRootedPolynomial pol;
		
		public MyProducer(List<Complex> roots) {
			pol = new ComplexRootedPolynomial(Complex.ONE, roots.stream().toArray(Complex[]::new));
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {

			ComplexPolynomial polynomial = pol.toComplexPolynom();
			ComplexPolynomial derived = polynomial.derive();
			
			int m = 16*16*16;
			int offset = 0;
			double convTreshold = 1E-3;
			double rootTreshold = 2E-3;
			short[] data = new short[width * height];
			for(int y = 0; y < height; y++) {
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

			observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
		}
	}
}
