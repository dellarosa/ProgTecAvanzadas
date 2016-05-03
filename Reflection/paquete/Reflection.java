package paquete;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Reflection {

	public static void main(String[] args) {
		Calculadora calc = new Calculadora();
		MiniCalc mini = new MiniCalc();

		Class clazz = calc.getClass();
		System.out.println("Nombre Completo: " + clazz.getName());
		System.out.println("Nombre         : " + clazz.getSimpleName());
		System.out.println("Metodos:");
		for (Method m : clazz.getDeclaredMethods()) {
			System.out.println(m);
		}
		System.out.println("Constructores:");
		for (Constructor m : clazz.getConstructors()) {
			System.out.println(m);
		}

		//
		// Class clazzm = mini.getClass();
		// System.out.println(clazzm.getName());

		try {
			Class<?> cla = Class.forName("paquete.Calculadora");

			Calculadora cal1 = (Calculadora) cla.newInstance();
			cal1.sumar(3);
			System.out.println(cal1.resultado);

			Calculadora cal2 = (Calculadora) cla.getConstructors()[1]
					.newInstance(10);
			cal2.sumar(7);
			System.out.println(cal2.resultado);

			Method limpiar = null;
			Method sumar = null;
			for (Method m : cla.getDeclaredMethods()) {
				if (m.getName().equals("limpiar"))
					limpiar = m;
				if (m.getName().equals("sumar"))
					sumar = m;
			}
			limpiar.invoke(cal1);
			System.out.println(cal1.getResultado());

			sumar.invoke(cal1, 55);
			System.out.println(cal1.getResultado());

			Method esc = clazz.getDeclaredMethod("escondido");
			esc.setAccessible(true);
			esc.invoke(cal1);

			for (Field f : clazz.getDeclaredFields()) {
				System.out.println(f.getModifiers() + "\t" + f.getName() + "\t"
						+ f.getType());

			}

			for (Annotation a : sumar.getDeclaredAnnotations()) {
				System.out.println(a.getClass().getName());
			}
			System.out.println(sumar.getDeclaredAnnotation(MyAnnotation.class));

			System.out.println(clazz.getDeclaredAnnotation(MyAnnotation.class));

			System.out.println(clazz.getSuperclass());

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
