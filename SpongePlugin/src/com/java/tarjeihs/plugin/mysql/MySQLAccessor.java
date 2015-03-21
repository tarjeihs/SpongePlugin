package com.java.tarjeihs.plugin.mysql;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.utilities.Regex;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	 */
//	protected boolean compare(String query, Object[] array, Object contains, Object compare) {
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

	protected final boolean executeUpdate(String query, Object[] array) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
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
				return true;
			}
		} catch (SQLException e) {
			Regex.println(e.getMessage());
			
			e.printStackTrace();
			
			return false;
		} finally {
			if (ps != null) { try { ps.close(); ps = null; } catch (SQLException ignored) {}}
		}
		return false;
	}

	protected final boolean executeUpdate(String query) {
		Connection conn = null;
		Statement stt = null;
		try {
			conn = getConnection();
			stt = conn.createStatement();

			stt.executeQuery(query);

			return true;
		} catch (SQLException e) {
			Regex.println(e.getMessage());
			return false;
		} finally {
			if (stt != null) { try { stt.close(); stt = null; } catch (SQLException ignored) {}}
		}
	}

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