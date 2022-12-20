package ads.util;

import java.lang.reflect.*;
import java.util.*;

/**
 * A class for interactive class testing
 */
public abstract class TestClass {

	private Object x;

	public Scanner getInput() {
		return input;
	}

	private Scanner input;


	private static final String prompt = ">>>>> ";
	
	public TestClass() {
		this(new Scanner(System.in));
	}

	public TestClass(Scanner scanner) {
		input = scanner;
	}


	private void writeln(String s) {
		System.out.println(prompt + s);
	}

	final private void afficherTitre(String s) {
		writeln("");
		writeln(s);
		writeln("");
	}

	final private void executer(Method m, String n, Class<?> c) {

		Object[] args = new Object[0];
		try {
			m.invoke(x,args);
		}
		catch ( IllegalAccessException iae ) {
			writeln("cannot make an instance");
		}
		catch ( IllegalArgumentException iarge ) {
			writeln("problem with arguments");
		}
		catch ( InvocationTargetException ite ) {
			writeln("the method " + n + " raised exception: " + ite.getTargetException());
			printStack(ite.getStackTrace());
		}
		catch ( NullPointerException npe ) {
			writeln("bad instanciation");
		}
	}

	private String[] lesMethodes(Class<?> laClasse) {
		TreeSet<String> v = new TreeSet<String>();
		Method[] m = null;

		try {
			m = laClasse.getDeclaredMethods();
			for ( int i = 0; i < m.length; i++ ) {
				Class<?>[] pt = m[i].getParameterTypes();
				Class<?> rt = m[i].getReturnType();
				if ( pt.length == 0 && rt.getName().equals("void") && Modifier.isPublic(m[i].getModifiers()) )
					v.add(m[i].getName());
			}
		}
		catch ( Exception e ) {}
		String[] s = new String[v.size()];
		int i = 0;
		for ( Iterator<String> it = v.iterator(); it.hasNext(); )
			s[i++] = it.next();
		return s;
	}

	private void dispo(String[] les_methodes) {
		if ( les_methodes.length == 0 )
			writeln("no test method found!");
		else {
			writeln("available methods are: ");
			writeln("");
			writeln((String) les_methodes[0]);
			for ( int i = 1; i < les_methodes.length; i++ )
				writeln((String) les_methodes[i]);
			writeln("");
		}
	}
	
	private String readString(String prompt) {
		System.out.println(prompt);
		return input.nextLine();
	}

	final protected void tester() {
		x = this;
		Class<?> laClasse = x.getClass();
		Method methode = null;
		Class<?>[] args = new Class[0];
		String nom = "";
		String fin = "q";
		String[] les_methodes = lesMethodes(laClasse);
		afficherTitre("Testing " + laClasse.getName());
		dispo(les_methodes);
		nom = readString(prompt + "method to test ('q' to quit) : ");

		while ( ! nom.equals(fin) ) {
			if ( nom.length() == 0 )
				dispo(les_methodes);
			else 
				try {
					methode = laClasse.getMethod(nom,args);
					if ( methode.getReturnType().getName().equals("void") ) {
						executer(methode,nom,laClasse);
					}
					else {
						writeln("the return type of " + nom + " is not void");
					}
				}
			catch ( SecurityException se ) {
				writeln("no method found");
			}
			catch ( NoSuchMethodException nsme ) {
				writeln("unknown method: " + nom);
			}
			nom = readString(prompt + "name of the method to test ('q' to quit) : ");
		}
	}

	private void printStack(StackTraceElement[] ste) {
		for ( int i = 0; i < ste.length; i++ ) {
			writeln(ste[i].toString());
		}
	}
}