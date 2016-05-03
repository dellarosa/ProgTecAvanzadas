import java.sql.SQLException;
import java.util.List;


public interface Sup<T, ID> {
	
	 public T queryForId(ID id) throws SQLException;

		/**
		 * Query for and return the first item in the object table which matches the PreparedQuery. See
		 * {@link #queryBuilder()} for more information. This can be used to return the object that matches a single unique
		 * column. You should use {@link #queryForId(Object)} if you want to query for the id column.
		 * 
		 * @param preparedQuery
		 *            Query used to match the objects in the database.
		 * @return The first object that matches the query.
		 * @throws SQLException
		 *             on any SQL problems.
		 */
	
		public List<T> queryForAll() throws SQLException;

}
