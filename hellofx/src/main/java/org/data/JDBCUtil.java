package org.data;

/**
 * @author Zhaoliang Ye 叶昭良(zl_ye@qny.chng.com.cn)
 * @version V0.1
 * @Title: JDBCUtil.java
 * @Description: 我的JDBCUTIL
 * @Package org.data
 * @Time: 2021-09-26 22:26
 */

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
/**
 * @author    叶昭良
 * @time      2015年2月17日下午11:31:53
 * @version   com.jdbc.testJDBCUtils V1.0
 * 			V2.0  改变配置文件变为为final，并把sql.properties文件提到
 * 				com.jdbc目录下 com/jdbc/test/sql.properties
 * 				测试通过
 *          V3.0 改变为静态代码段 加载驱动！ 并把加载配置文件
 *               也放在静态代码段中！仅仅加载一次即可！而不需要
 *               一直加载
 *          V4.0 修正了没有close的问题！！防止一直连着。 明白了吃异常的过程
 *                在类库中不知道怎么处理！直接抛出异常即可！让调用者自己去处理！
 *                咱们提供方法，不提供异常的处理！打印异常不叫做处理！而叫做“吃异常”
 *          V5.0  不吃异常的方式就是 throw new RuntimeException ，静态代码段
 *          	无法throw checkedException检查异常
 *                没想到经过这样的过程封装比我原先的好看多了！而且逻辑更加清晰
 *          V6.0 有没有理解清楚，我们实际用的较多executeUpdate和executeQuery的无conn
 *               的形式，而带conn只是一个中间过程！ 并且无conn的会调用有conn的形式！
 *              再次修改！ 注意观看executeUpdate的实现
 *          V7.0  再次改进！不需要把executeQuery的connection关闭
 *                由于PreparedStatement 来自Statement
 *                rs.getStatement() 表示返回一个statement，而不会关闭PreparedStatement
 *                常见了一个closeAll(),这也是为什么 暂时不关闭查询连接的原因，同时不关闭
 *                方便进行多次查询，反复查询
 *
 *           V8.0  cachedRowSet 是一个缓冲的mysql字符集，连接中断！把所有数据缓冲到客户端！
 *                 不需要再次连接，所以关闭Connection也是可以
 *                 但是这个栈内存  暂时不使用
 *
 *=思路总结：
1：利用配置表，载入mysql和数据库信息
2：利用静态代码段载入驱动（只需要一次）
3： 编写 带connection的executeUpdate和executeResult,并且在executeResult不要关闭任何信息，待统一关闭
4： 编写 不带Connection的executeUpdate和executeResult,内部调用带Connction版本的而寒暑
5： 编写closeQuietly函数  和closeAll函数
 *
 */

public class JDBCUtil {
/**
     * @param args
     */
    private static final String mysqlDriver;
    private static final String connectDatabase;
    private static final String userName;
    private static final String Password;
    static
    {
        InputStream is = null;
        //InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("sql.properties");
        Properties ps  = new Properties();
        //Connection c1 = null;
        try
        {
            //is = JDBCUtils.class.getClassLoader().getResourceAsStream(""
            //		+ "com/jdbc/test/sql.properties");
            is = JDBCUtil.class.getResourceAsStream("jdbc.properties");
            //这种情况省略宝和getClassLoader()
            ps.load(is);
            mysqlDriver = ps.getProperty("jdbc.driver");
            //			System.out.println(mysqlDriver);
            connectDatabase = ps.getProperty("jdbc.url");
            //			System.out.println(connectDatabase);
            userName = ps.getProperty("jdbc.username");
            //			System.out.println(userName);
            Password = ps.getProperty("jdbc.password");
            //			System.out.println(Password);
            //仅仅在静态代码段中加载一次驱动即可（在运行中）

        } catch ( IOException e)
        {
            // TODO Auto-generated catch block
            //System.out.println("配置文件加载失败"+e.getMessage());
            throw new RuntimeException("配置文件sql.properties加载失败"+e.getMessage());
            //return null;
        }
        try
        {
            Class.forName(mysqlDriver); //仅仅在程序运行中加载一次驱动即可！！！
        } catch (ClassNotFoundException e)
        {
            // TODO Auto-generated catch block
            //			System.out.println("驱动加载失败"+e.getMessage());
            throw new RuntimeException("驱动加载失败"+e.getMessage());
        }
    }
/**
     * 创建数据库的连接
     * @return   返回mysql的连接
     */
    public static Connection createConnection()
    {
        Connection c1  = null;
        try
        {

            c1 = DriverManager.getConnection(connectDatabase,userName,Password);
            System.out.println("连接成功");
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            System.out.println("数据库连接创建失败"+e.getMessage());
            return null;
        }
        return c1;
    }
    /**
     *
     * @param sql        sql的update,delete,insert等修改数据库的语句
     * @param parameter  参数化的update,delete,insert等的设置
     * @return           返回被影响的函数
     * @throws SQLException   抛出SQLException由用户自己去特殊处理
     */
    public static int executeUpdate(String sql,Object... parameter) throws SQLException
    {
        Connection conn = null;
        try
        {
            conn = JDBCUtil.createConnection();
            return executeUpdate(conn, sql, parameter);
        }finally
        {
            JDBCUtil.closeQuietly(conn);
        }
    }
    /**
     * @param conn       数据库的连接
     * @param sql        sql的update,delete,insert等修改数据库的语句
     * @param parameter  参数化的update,delete,insert等的设置
     * @return           返回被影响的函数
     * @throws SQLException   抛出SQLException由用户自己去特殊处理
     */
    public static int executeUpdate(Connection conn,String sql,Object... parameter) throws SQLException
    {
        PreparedStatement ps = null;
        int rows = 0;
        try
        {
            ps = conn.prepareStatement(sql);
            int i = 1;
            if(parameter != null)
            {
                for(Object pa1:parameter)
                {
                    ps.setObject(i, pa1);
                    i++;
                }
            }
            rows = ps.executeUpdate();
        }finally
        {
            JDBCUtil.closeQuietly(ps);
        }

        System.out.println("有"+rows+"条记录被影响");
        return rows;
    }
    /**
     *
     * @param sql        sql的select不修改数据库的语句
     * @param parameter  参数化的查询，参数集的设置
     * @return           返回查询的数据集
     * @throws SQLException   抛出SQLException由用户自己去特殊处理
     */
    public static ResultSet executeQuery(String sql, Object... parameter) throws SQLException
    {
        Connection conn = null;

        conn = JDBCUtil.createConnection();
        return executeQuery(conn,sql, parameter);
        /*finally
          {
          JDBCUtils.closeQuietly(conn);
          }*/

    }
    /**
     * @param conn       数据库连接
     * @param sql        sql的select不修改数据库的语句
     * @param parameter  参数化的查询，参数集的设置
     * @return           返回查询的数据集
     * @throws SQLException   抛出SQLException由用户自己去特殊处理
     */
    public static ResultSet executeQuery(Connection conn,String sql,Object... parameter) throws SQLException
    {
        PreparedStatement ps = null;
        ps = conn.prepareStatement(sql);
        int i =1;
        if(parameter != null)
        {
            for(Object pa1:parameter)
            {
                ps.setObject(i, pa1);
                i++;
            }
        }
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    /**
     * 从resultSet中关闭所有的连接
     * @param rs
     */
    static void closeAll(ResultSet rs)
    {
        if(rs == null)
        {
            return;
        }
        try
        {
            JDBCUtil.closeQuietly(rs.getStatement().getConnection());
            JDBCUtil.closeQuietly(rs.getStatement());
            JDBCUtil.closeAll(rs);
        }catch(SQLException e)
        {

        }
    }
    /**
     *  关闭PreparedStatment连接
     * @param ps   PreparedStatment对象
     */
    //static void closeQuietly(PreparedStatement ps)
    static void closeQuietly(Statement ps)
    {
        if(ps!= null)
        {
            try
            {
                ps.close();
            }catch(SQLException e)
            {

            }
        }
    }
    /**
     *  关闭Connection连接
     * @param conn   Connection对象
     */
    static void closeQuietly(Connection conn)
    {
        if(conn!= null)
        {
            try
            {
                conn.close();
            }catch(SQLException e)
            {

            }
        }
    }
    /**
     *  关闭ResultSet连接
     * @param rs   ResultSet对象
     */
    static void closeQuietly(ResultSet rs)
    {
        if(rs!= null)
        {
            try
            {
                rs.close();
            }catch(SQLException e)
            {

            }
        }
    }
    /**
     *   封装批处理提交会使用到的rollback函数
     * @param conn  Connection 连接类型
     */
    static void rollback(Connection conn)
    {
        if(conn!= null)
        {
            try
            {
                conn.rollback();
            }catch(SQLException e)
            {

            }
        }
    }

}
