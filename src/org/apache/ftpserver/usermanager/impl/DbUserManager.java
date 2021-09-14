package org.apache.ftpserver.usermanager.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.sql.DataSource;
import org.apache.ftpserver.FtpServerConfigurationException;
import org.apache.ftpserver.ftplet.Authentication;
import org.apache.ftpserver.ftplet.AuthenticationFailedException;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.User;
import org.apache.ftpserver.usermanager.AnonymousAuthentication;
import org.apache.ftpserver.usermanager.PasswordEncryptor;
import org.apache.ftpserver.usermanager.UsernamePasswordAuthentication;
import org.apache.ftpserver.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbUserManager extends AbstractUserManager
{
  private final Logger LOG = LoggerFactory.getLogger(DbUserManager.class);
  private String insertUserStmt;
  private String updateUserStmt;
  private String deleteUserStmt;
  private String selectUserStmt;
  private String selectAllStmt;
  private String isAdminStmt;
  private String authenticateStmt;
  private DataSource dataSource;

  public DbUserManager(DataSource dataSource, String selectAllStmt, String selectUserStmt, String insertUserStmt, String updateUserStmt, String deleteUserStmt, String authenticateStmt, String isAdminStmt, PasswordEncryptor passwordEncryptor, String adminName)
  {
    super(adminName, passwordEncryptor);
    this.dataSource = dataSource;
    this.selectAllStmt = selectAllStmt;
    this.selectUserStmt = selectUserStmt;
    this.insertUserStmt = insertUserStmt;
    this.updateUserStmt = updateUserStmt;
    this.deleteUserStmt = deleteUserStmt;
    this.authenticateStmt = authenticateStmt;
    this.isAdminStmt = isAdminStmt;

    Connection con = null;
    try
    {
      con = createConnection();

      this.LOG.info("Database connection opened.");
    } catch (SQLException ex) {
      this.LOG.error("Failed to open connection to user database", ex);
      throw new FtpServerConfigurationException("Failed to open connection to user database", ex);
    }
    finally {
      closeQuitely(con);
    }
  }

  public DataSource getDataSource()
  {
    return this.dataSource;
  }

  public void setDataSource(DataSource dataSource)
  {
    this.dataSource = dataSource;
  }

  public String getSqlUserInsert()
  {
    return this.insertUserStmt;
  }

  public void setSqlUserInsert(String sql)
  {
    this.insertUserStmt = sql;
  }

  public String getSqlUserDelete()
  {
    return this.deleteUserStmt;
  }

  public void setSqlUserDelete(String sql)
  {
    this.deleteUserStmt = sql;
  }

  public String getSqlUserUpdate()
  {
    return this.updateUserStmt;
  }

  public void setSqlUserUpdate(String sql)
  {
    this.updateUserStmt = sql;
  }

  public String getSqlUserSelect()
  {
    return this.selectUserStmt;
  }

  public void setSqlUserSelect(String sql)
  {
    this.selectUserStmt = sql;
  }

  public String getSqlUserSelectAll()
  {
    return this.selectAllStmt;
  }

  public void setSqlUserSelectAll(String sql)
  {
    this.selectAllStmt = sql;
  }

  public String getSqlUserAuthenticate()
  {
    return this.authenticateStmt;
  }

  public void setSqlUserAuthenticate(String sql)
  {
    this.authenticateStmt = sql;
  }

  public String getSqlUserAdmin()
  {
    return this.isAdminStmt;
  }

  public void setSqlUserAdmin(String sql)
  {
    this.isAdminStmt = sql;
  }

  public boolean isAdmin(String login)
    throws FtpException
  {
    if (login == null) {
      return false;
    }

    Statement stmt = null;
    ResultSet rs = null;
    try
    {
      HashMap map = new HashMap();
      map.put("userid", escapeString(login));
      String sql = StringUtils.replaceString(this.isAdminStmt, map);
      this.LOG.info(sql);

      stmt = createConnection().createStatement();
      rs = stmt.executeQuery(sql);
      return rs.next();
    } catch (SQLException ex) {
      this.LOG.error("DbUserManager.isAdmin()", ex);
      throw new FtpException("DbUserManager.isAdmin()", ex);
    } finally {
      closeQuitely(rs);
      closeQuitely(stmt);
    }
  }

  protected Connection createConnection()
    throws SQLException
  {
    Connection connection = this.dataSource.getConnection();
    connection.setAutoCommit(true);

    return connection;
  }

  public void delete(String name)
    throws FtpException
  {
    HashMap map = new HashMap();
    map.put("userid", escapeString(name));
    String sql = StringUtils.replaceString(this.deleteUserStmt, map);
    this.LOG.info(sql);

    Statement stmt = null;
    try {
      stmt = createConnection().createStatement();
      stmt.executeUpdate(sql);
    } catch (SQLException ex) {
      this.LOG.error("DbUserManager.delete()", ex);
      throw new FtpException("DbUserManager.delete()", ex);
    } finally {
      closeQuitely(stmt);
    }
  }

  public void save(User user)
    throws FtpException
  {
    if (user.getName() == null) {
      throw new NullPointerException("User name is null.");
    }

    Statement stmt = null;
    try
    {
      HashMap map = new HashMap();
      map.put("userid", escapeString(user.getName()));

      String password = null;
      if (user.getPassword() != null)
      {
        password = getPasswordEncryptor().encrypt(user.getPassword());
      }
      else
      {
        ResultSet rs = null;
        try
        {
          User userWithPassword = selectUserByName(user.getName());

          if (userWithPassword != null)
          {
            password = userWithPassword.getPassword();
          }
        }
        finally {
        }
      }
      map.put("userpassword", escapeString(password));

      String home = user.getHomeDirectory();
      if (home == null) {
        home = "/";
      }
      map.put("homedirectory", escapeString(home));
      map.put("enableflag", String.valueOf(user.getEnabled()));

      map.put("writepermission", String.valueOf(user.authorize(new WriteRequest()) != null));

      map.put("idletime", Integer.valueOf(user.getMaxIdleTime()));

      TransferRateRequest transferRateRequest = new TransferRateRequest();
      transferRateRequest = (TransferRateRequest)user.authorize(transferRateRequest);

      if (transferRateRequest != null) {
        map.put("uploadrate", Integer.valueOf(transferRateRequest.getMaxUploadRate()));

        map.put("downloadrate", Integer.valueOf(transferRateRequest.getMaxDownloadRate()));
      }
      else {
        map.put("uploadrate", Integer.valueOf(0));
        map.put("downloadrate", Integer.valueOf(0));
      }

      ConcurrentLoginRequest concurrentLoginRequest = new ConcurrentLoginRequest(0, 0);

      concurrentLoginRequest = (ConcurrentLoginRequest)user.authorize(concurrentLoginRequest);

      if (concurrentLoginRequest != null) {
        map.put("maxloginnumber", Integer.valueOf(concurrentLoginRequest.getMaxConcurrentLogins()));

        map.put("maxloginperip", Integer.valueOf(concurrentLoginRequest.getMaxConcurrentLoginsPerIP()));
      }
      else {
        map.put("maxloginnumber", Integer.valueOf(0));
        map.put("maxloginperip", Integer.valueOf(0));
      }

      String sql = null;
      if (!doesExist(user.getName()))
        sql = StringUtils.replaceString(this.insertUserStmt, map);
      else {
        sql = StringUtils.replaceString(this.updateUserStmt, map);
      }
      this.LOG.info(sql);

      stmt = createConnection().createStatement();
      stmt.executeUpdate(sql);
    } catch (SQLException ex) {
      this.LOG.error("DbUserManager.save()", ex);
      throw new FtpException("DbUserManager.save()", ex);
    } finally {
      closeQuitely(stmt);
    }
  }

  private void closeQuitely(Statement stmt) {
    if (stmt != null) {
      Connection con = null;
      try {
        con = stmt.getConnection();
      } catch (Exception e) {
      }
      try {
        stmt.close();
      }
      catch (SQLException e) {
      }
      closeQuitely(con);
    }
  }

  private void closeQuitely(ResultSet rs) {
    if (rs != null)
      try {
        rs.close();
      }
      catch (SQLException e)
      {
      }
  }

  protected void closeQuitely(Connection con) {
    if (con != null)
      try {
        con.close();
      }
      catch (SQLException e)
      {
      }
  }

  private BaseUser selectUserByName(String name) throws SQLException
  {
    HashMap map = new HashMap();
    map.put("userid", escapeString(name));
    String sql = StringUtils.replaceString(this.selectUserStmt, map);
    this.LOG.info(sql);

    Statement stmt = null;
    ResultSet rs = null;
    try
    {
      stmt = createConnection().createStatement();
      rs = stmt.executeQuery(sql);

      BaseUser thisUser = null;
      List authorities;
      if (rs.next()) {
        thisUser = new BaseUser();
        thisUser.setName(rs.getString("userid"));
        thisUser.setPassword(rs.getString("userpassword"));
        thisUser.setHomeDirectory(rs.getString("homedirectory"));
        thisUser.setEnabled(Boolean.valueOf(rs.getString("enableflag")));
        thisUser.setMaxIdleTime(rs.getInt("idletime"));

        authorities = new ArrayList();
        if (Boolean.valueOf(rs.getString("writepermission"))) {
          authorities.add(new WritePermission());
        }

        authorities.add(new ConcurrentLoginPermission(rs.getInt("maxloginnumber"), rs.getInt("maxloginperip")));

        authorities.add(new TransferRatePermission(rs.getInt("downloadrate"), rs.getInt("uploadrate")));

        thisUser.setAuthorities(authorities);
      }
      return thisUser;
    }
    finally {
      closeQuitely(rs);
      closeQuitely(stmt);
    }
  }

  public User getUserByName(String name)
    throws FtpException
  {
    Statement stmt = null;
    ResultSet rs = null;
    try
    {
      BaseUser user = selectUserByName(name);

      if (user != null)
      {
        user.setPassword(null);
      }
      return user;
    }
    catch (SQLException ex)
    {
      this.LOG.error("DbUserManager.getUserByName()", ex);
      throw new FtpException("DbUserManager.getUserByName()", ex);
    } finally {
      closeQuitely(rs);
      closeQuitely(stmt);
    }
  }

  public boolean doesExist(String name)
    throws FtpException
  {
    Statement stmt = null;
    ResultSet rs = null;
    try
    {
      HashMap map = new HashMap();
      map.put("userid", escapeString(name));
      String sql = StringUtils.replaceString(this.selectUserStmt, map);
      this.LOG.info(sql);

      stmt = createConnection().createStatement();
      rs = stmt.executeQuery(sql);
      return rs.next();
    } catch (SQLException ex) {
      this.LOG.error("DbUserManager.doesExist()", ex);
      throw new FtpException("DbUserManager.doesExist()", ex);
    } finally {
      closeQuitely(rs);
      closeQuitely(stmt);
    }
  }

  public String[] getAllUserNames()
    throws FtpException
  {
    Statement stmt = null;
    ResultSet rs = null;
    try
    {
      String sql = this.selectAllStmt;
      this.LOG.info(sql);

      stmt = createConnection().createStatement();
      rs = stmt.executeQuery(sql);

      ArrayList names = new ArrayList();
      while (rs.next()) {
        names.add(rs.getString("userid"));
      }
      return (String[])names.toArray(new String[0]);
    } catch (SQLException ex) {
      this.LOG.error("DbUserManager.getAllUserNames()", ex);
      throw new FtpException("DbUserManager.getAllUserNames()", ex);
    } finally {
      closeQuitely(rs);
      closeQuitely(stmt);
    }
  }

  public User authenticate(Authentication authentication)
    throws AuthenticationFailedException
  {
    if ((authentication instanceof UsernamePasswordAuthentication)) {
      UsernamePasswordAuthentication upauth = (UsernamePasswordAuthentication)authentication;

      String user = upauth.getUsername();
      String password = upauth.getPassword();

      if (user == null) {
        throw new AuthenticationFailedException("Authentication failed");
      }

      if (password == null) {
        password = "";
      }

      Statement stmt = null;
      ResultSet rs = null;
      try
      {
        HashMap map = new HashMap();
        map.put("userid", escapeString(user));
        String sql = StringUtils.replaceString(this.authenticateStmt, map);
        this.LOG.info(sql);

        stmt = createConnection().createStatement();
        rs = stmt.executeQuery(sql);
        if (rs.next()) {
          try {
            String storedPassword = rs.getString("userpassword");
            if (getPasswordEncryptor().matches(password, storedPassword)) {
              return getUserByName(user);
            }
            throw new AuthenticationFailedException("Authentication failed");
          }
          catch (FtpException e)
          {
            throw new AuthenticationFailedException("Authentication failed", e);
          }
        }

        throw new AuthenticationFailedException("Authentication failed");
      }
      catch (SQLException ex)
      {
        this.LOG.error("DbUserManager.authenticate()", ex);
        throw new AuthenticationFailedException("Authentication failed", ex);
      }
      finally {
        closeQuitely(rs);
        closeQuitely(stmt);
      }
    }
    if ((authentication instanceof AnonymousAuthentication)) {
      try {
        if (doesExist("anonymous")) {
          return getUserByName("anonymous");
        }
        throw new AuthenticationFailedException("Authentication failed");
      }
      catch (AuthenticationFailedException e)
      {
        throw e;
      } catch (FtpException e) {
        throw new AuthenticationFailedException("Authentication failed", e);
      }
    }

    throw new IllegalArgumentException("Authentication not supported by this user manager");
  }

  private String escapeString(String input)
  {
    if (input == null) {
      return input;
    }

    StringBuilder valBuf = new StringBuilder(input);
    for (int i = 0; i < valBuf.length(); i++) {
      char ch = valBuf.charAt(i);
      if ((ch == '\'') || (ch == '\\') || (ch == '$') || (ch == '^') || (ch == '[') || (ch == ']') || (ch == '{') || (ch == '}'))
      {
        valBuf.insert(i, '\\');
        i++;
      }
    }
    return valBuf.toString();
  }
}