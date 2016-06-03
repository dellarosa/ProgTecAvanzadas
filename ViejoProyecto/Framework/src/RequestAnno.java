public @interface RequestAnno {

		/**
		 * The name of the column in the database. If not set then the name is taken from the class name lowercased.
		 */
		String direccion() default "";

		
	
}
