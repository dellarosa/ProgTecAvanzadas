package paquete;
import java.lang.annotation.Inherited;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;


@Target(ElementType.METHOD)
@Inherited
public @interface MyAnnotation {
	boolean usable();
}
