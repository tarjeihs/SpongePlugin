package com.java.tarjeihs.plugin.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.java.tarjeihs.plugin.JPlugin;

public class MySQLAccessor {
	protected final JPlugin plugin;

	public MySQLAccessor(JPlugin instance) {
		this.plugin = instance;
	}
	
	/**
	 * 
	 * @param query The query to be asked
	 * @param array Replaced question-marks from PS
	 * @param contains Take out a object (int or string) from ResultSet
	 * @param compare Compare the resultset
	 * @return Whether the compare was true or false
	 *
	 */
//	private boolean compare(String query, Object[] array, Object contains, Object compare) {
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		try {
//			conn = getConnection();
//			ps = conn.prepareStatement(query);
//						
//			if (array != null) {
//				for (int i = 0; i < array.length; i++) {
//					if ((array[i] instanceof String)) {
//						ps.setString(i + 1, (String) array[i]);
//					} else if ((array[i] instanceof Integer)) {
//						ps.setInt(i + 1, ((Integer) array[i]).intValue());
//					}
//				}
//			}
//			
//			rs = ps.executeQuery();
//			int x = 0;
//			String z = null;
//			while (rs.next()) {
//				if (contains instanceof String) {
//					z = rs.getString((String) contains);
//				} else if (contains instanceof Integer) {
//					x = rs.getInt((int) contains);
//				}
//			}
//			
//			if (x == 0 || z == null) {
//				return false;
//			}
//			
//			if (compare instanceof String) {
//				if (compare.equals(z)) {
//					return true;
//				} else {
//					return false;
//				}
//			} else if (compare instanceof Integer) {
//				if (compare.equals(x)) {
//					return true;
//				} else {
//					return false;
//				}
//			}
//			
//		} catch (Exception e) {
//			Regex.println(e.getMessage());
//		} finally {
//			if (ps != null) { try { ps.close(); ps = null; } catch (SQLException ignored) {}}
//			if (rs != null) { try { rs.close(); rs = null; } catch (SQLException ignored) {}}
//	    }
//		return false;
//	}
	
	/*
	 * Have to add more features for this
	 */
//	private ResultSet getGeneratedKey(PreparedStatement ps) throws SQLException {
//		ResultSet rs = ps.getGeneratedKeys();
//		return rs;
//	}
//	
//	protected final long serializeObject(String query, Object... object) {
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		long id = -1; // must be -1, else JSON will read it as a proper working ID from db 
//		try {
//			conn = getConnection();
//			ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
//			if (object != null) {
//				for (int i = 0; i < object.length; i++) {
//					if (object[i] instanceof String) {
//						ps.setString(i, (String) object[i]);
//					} else if (object[i] instanceof Object) {
//						ps.setObject(i, (Object) object[i]);
//					} else if (object[i] instanceof Integer) {
//						ps.setInt(i, (int) object[i]);
//					}
//				}
//			}
//			rs = getGeneratedKey(ps);
//			if (rs.next()) {
//				id = rs.getInt(1);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (ps != null) { try { ps.close(); ps = null; } catch (SQLException ignored) {}}
//			if (rs != null) { try { rs.close(); rs = null; } catch (SQLException ignored) {}}
//		}
//		
//		return id;
//	}
//	
	protected boolean exists(String query, Object obj) {
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			
			if (obj instanceof String) {
				ps.setString(1, (String) obj); 
			} else {
				ps.setInt(1, (int) obj); 
			}
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) { try { ps.close(); ps = null; } catch (SQLException ignored) {}}
			if (rs != null) { try { rs.close(); rs = null; } catch (SQLException ignored) {}}
		}
		return false;
	}
	

	/**
	 * 
	 * @param query Query to be asked
	 * @param id To retrieve the information with (has to be long incase the databases counter gets to large)
	 * @return Serialized class
	 */
//	protected final Object deserializeObject(String query, long id) {
//		PreparedStatement ps = null;
//		Connection conn = null;
//		ResultSet rs = null;
//		Object object = null;
//		try {
//			conn = getConnection();
//			ps = conn.prepareStatement(query);
//			
//			ps.setLong(1,  id);
//			
//			rs = ps.executeQuery();
//			
//			if (rs.next()) {
//				byte[] buffer = rs.getBytes(1);
//				ObjectInputStream ois = null;
//				if (buffer != null) 
//					ois = new ObjectInputStream(new ByteArrayInputStream(buffer));
//				
//				object = ois.readObject();
//				
//				if (ois != null) 
//					ois.close();
//			}
//			
//		} catch (SQLException | IOException | ClassNotFoundException e) {
//			e.printStackTrace();
//		} finally {
//			if (ps != null) { try { ps.close(); ps = null; } catch (SQLException ignored) {}}
//			if (rs != null) { try { rs.close(); rs = null; } catch (SQLException ignored) {}}
//		}
//		
//		return object;
//	}

	/**
	 * 
	 * @param query Query to be asked
	 * @param array The questionmarks to be replaced with
	 * @param retrieve What you want to retrieve
	 * @return Whatever u have retrieved in a Builder.toString
	 */
	protected String get(String query, Object[] array, Object[] retrieve) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			if (array != null) {
				for (int i = 0; i < array.length; i++) {
					if ((array[i] instanceof String)) {
						ps.setString(i + 1, (String) array[i]);
					} else if ((array[i] instanceof Integer)) {
						ps.setInt(i + 1, ((Integer) array[i]).intValue());
					}
				}
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				for (Object o : retrieve) {
					if (o instanceof String) {
						sb.append(rs.getString((String) o));
					} else if (o instanceof Integer) {
						sb.append(rs.getInt((int) o));
					}
				}
			}
		} catch (SQLException e) {
//			Regex.println(e.getMessage() + " " + e.getErrorCode() + " " + this.getClass().getName());
			e.printStackTrace();
		} finally {
			if (ps != null) { try { ps.close(); ps = null; } catch (SQLException ignored) {}}
			if (rs != null) { try { rs.close(); rs = null; } catch (SQLException ignored) {}}
		}
		return sb.toString();
	}

	protected final int executeUpdate(String query, Object[] array) {
		Connection conn = null;
		PreparedStatement ps = null;
		int id = -1;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			if (array != null) {
				for (int i = 0; i < array.length; i++) {
					if ((array[i] instanceof String)) {
						ps.setString(i + 1, (String) array[i]);
					} else if ((array[i] instanceof Integer)) {
						ps.setInt(i + 1, ((Integer) array[i]).intValue());
					} else if ((array[i] instanceof Object)) {
						ps.setObject(i + 1, array[i]);
					}
				}
				ps.executeUpdate();
				
				try (ResultSet key = ps.getGeneratedKeys()){ // only if u insert something
					if (key.next()) {
						id = key.getInt(1);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) { try { ps.close(); ps = null; } catch (SQLException ignored) {}}
		}
		return id;
	}

//	protected final boolean executeUpdate(String query) {
//		Connection conn = null;
//		Statement stt = null;
//		try {
//			conn = getConnection();
//			stt = conn.createStatement();
//
//			stt.executeQuery(query);
//
//			return true;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return false;
//		} finally {
//			if (stt != null) { try { stt.close(); stt = null; } catch (SQLException ignored) {}}
//		}
//	}

	protected JPlugin getInstance() {
		if (this.plugin == null) {
			throw new NullPointerException("Plugin cannot be null");
		}
		return this.plugin;
	}

	protected Connection getConnection() {
		return this.plugin.getSQLHandler().getConnection();
	}
}