package org.data;

/**
 * @author Zhaoliang Ye 叶昭良(zl_ye@qny.chng.com.cn)
 * @version V0.1
 * @Title: MYORM.java
 * @Description: (用一句话描述该文件做什么 ?)
 * @Package org.data
 * @Time: 2021-09-26 22:32
 */
/**
 *  对象 Student
 *  mysql表   Student
 *
 *  类 表对应！  通过对象传递进数组，并利用内省机制，获得类中的所有属性！
 *   并获得其中的值，然后组合成为一个sql语句，并执行JDBCUtils 来进行
 *   增、删、修改、查等操作
 *
 *   目的 ：对象 --mysql表 进行一一对应并且可以进行传递
 *   简单的MyOrm.insert(
 *              delete
 *              select
 *              update 等
 *
 *    约束条件：主键必须为Id，且是int类型，自动递增
 *             字段名必须和属性名一样   也就是类的属性和表的字段保持一致
 *             表名字和类的名字一样
 */

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author    叶昭良
 * @time      2015年3月4日下午8:57:46
 * @version   com.introspect.testMyORM V1.0
 */

public class MYORM {
/**
     * sql = insert into Person(fieldName) values(?,?..)
     *         1                 2             3   4
     *  JDBCUTils.executeUpdate(sql,propValue)
     * @param obj
     */
    public static void insert(Object obj) throws SQLException
    {
        Class clazz = obj.getClass();
        BeanInfo beanInfo = null;
        beanInfo = getBeanInfo(clazz);

        //用于数据库表
        String className = clazz.getSimpleName();

        PropertyDescriptor[] pro = beanInfo.getPropertyDescriptors();
        List<String> listFieldName = new ArrayList<String>();
        for(PropertyDescriptor temp:pro)
        {
            String propName = temp.getName();
            if(!propName.equals("id") && !propName.equals("class"))
            {
                //扣除 id和class这两个内部字段
                listFieldName.add(propName);
            }
        }

        StringBuilder sbSQL = new StringBuilder();

        //第一步 拼接  insert
        sbSQL.append("insert into ").append(className);
        //第二步 拼接  字段
        String fieldNames = listFieldName.toString();
        sbSQL.append(fieldNames.replace('[', '(').replace(']', ')'));

        //第三步  拼接  值
        sbSQL.append(" values");

        //第四步  拼接  添加问号
        char[] paramMarkArray  = new char[listFieldName.size()];
        for(int i = 0 ; i < listFieldName.size() ; i++)
        {
            paramMarkArray[i] = '?';
        }
        sbSQL.append(Arrays.toString(paramMarkArray).replace('[', '(').replace
                (']', ')'));

        //最后一步    调用JDBCUtils的 executeUpdate
        List<Object> paramValues = new ArrayList<Object>();
        for(String propName : listFieldName)
        {
            PropertyDescriptor propDesc = findPropertyDescriptor(propName, pro);
            Object propValue =null;
            //obj传入的类或者表名的对象
            propValue = invoke(propDesc, obj);
            paramValues.add(propValue);
        }
        //最终过程  toString  toArray的转换
        JDBCUtil.executeUpdate(sbSQL.toString(), paramValues.toArray());

    }
    /**
     *
     * @param clazz
     * @return  返回一个beanInfo对象
     */
    private static BeanInfo getBeanInfo(Class clazz)
    {
        BeanInfo beanInfo = null;
        try
        {
            beanInfo = Introspector.getBeanInfo(clazz);
        }catch(IntrospectionException e)
        {
            throw new RuntimeException("内省出错！"+e.getMessage());
        }
        return beanInfo;
    }
    /**
     *  从propDesc中找名字为propName的PropertyDescriptor
     * @param propName
     * @param prop
     * @return
     */
    private static PropertyDescriptor findPropertyDescriptor(String propName,
                                                             PropertyDescriptor[] prop)
    {
        for(PropertyDescriptor temp : prop)
        {
            if(temp.getName().equals(propName))
            {
                return temp;
            }
        }
        return null;
    }
    /**
     *  执行某个字段的读方法  并返回 propValue
     * @param propDesc
     * @param obj
     * @return
     */
    private static Object invoke(PropertyDescriptor propDesc,Object obj)
    {
        Object propValue = null;
        try
        {
            propValue = propDesc.getReadMethod().invoke(obj);
        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e)
        {
            // TODO Auto-generated catch block
            throw new RuntimeException("获取"+propDesc.getName()+"错误");
        }
        return propValue;
    }
    //------------------------insert 程序 结束----------------
    /**
     * delete from className where id = ?
     *
     *
     *   删除clazz对应表中的字段id为id的值   delete(Person.class,5);
     * @param clazz
     * @param id
     */
    public static void delete(Class clazz, int id) throws SQLException
    {
        BeanInfo beanInfo = null;
        beanInfo = getBeanInfo(clazz);

        //用于数据库表
        String className = clazz.getSimpleName();

        PropertyDescriptor[] pro = beanInfo.getPropertyDescriptors();
        List<String> listFieldName = new ArrayList<String>();


        StringBuilder sbSQL = new StringBuilder();

        //第一步 拼接  insert
        sbSQL.append("delete from ").append(className).append(" where id = ?");

        JDBCUtil.executeUpdate(sbSQL.toString(), id);

    }


    /**
     * select * from Person where id = 2;
     *
     *   获取clazz对应表中id为字段为id的对应航的   并且填充到对象中
     *   Person p1 = (Person)select(Person.class,2)
     *   p1.getName(), p1.getAge();
     * @param clazz
     * @param num
     * @return
     */
    //----------------------delete 程序结束--------------------
    public static Object select(Class clazz,String num) throws SQLException
    {
        Object b1 = null;
        b1 = getInstance(clazz);

        BeanInfo beanInfo = null;
        beanInfo = getBeanInfo(clazz);

        //用于数据库表
        String className = clazz.getSimpleName();

        PropertyDescriptor[] pro = beanInfo.getPropertyDescriptors();
        List<String> listFieldName = new ArrayList<String>();
        for(PropertyDescriptor temp:pro)
        {
            String propName = temp.getName();
            if(!propName.equals("id") && !propName.equals("class"))
            {
                //扣除 id和class这两个内部字段
                listFieldName.add(propName);
            }
        }

        StringBuilder sbSQL = new StringBuilder();

        //第一步 拼接  insert
        sbSQL.append("select * from ").append(className).append(" where num = ?");
        //获取到 ResultSet
        ResultSet rs = JDBCUtil.executeQuery(sbSQL.toString(), num);
        if(!rs.next())
        {
            System.out.println("当前版本没有"+num+"的信息");
            //			return;
        }else
        {
            System.out.println("当前版本有"+num+"的信息");
            for(String propName : listFieldName)
            {
                PropertyDescriptor propDesc = findPropertyDescriptor(propName, pro);
                //obj传入的类或者表名的对象
                invoke(propDesc, b1,rs.getObject(propName));
                //invoke(propDesc, b1,rs.getString(propName));
            }
        }
        //设置num字段
        PropertyDescriptor propDesc1 = findPropertyDescriptor("num", pro);
        invoke(propDesc1, b1,num);
        System.out.println("成功");
        return b1;

        //先非泛型  再泛型的selectById
    }

    public static Object select(Class clazz) throws SQLException
    {
        int count = 0;
        //用于数据库表
        String className = clazz.getSimpleName();
        StringBuilder sbSQL = new StringBuilder();

        //第一步 拼接  insert
        sbSQL.append("select count(*) tb from ").append(className);
        //获取到 ResultSet
        ResultSet rs = JDBCUtil.executeQuery(sbSQL.toString());
        while(rs.next())
        {
            count = rs.getInt("tb");
            System.out.println("总共有"+count+"个数据");
        }

        System.out.println("成功");
        return count;

        //先非泛型  再泛型的selectById
    }
    private static Object getInstance(Class clazz)
    {
        Object b1 = null;
        try
        {
            b1 = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return b1;
    }
    //private static void invoke(PropertyDescriptor propDesc,Object obj,String value)

    private static void invoke(PropertyDescriptor propDesc,Object obj,Object value)
    {
        //有问题 因为
        /*try
          {
          propDesc.getWriteMethod().invoke(obj,value);
          } catch (IllegalAccessException | IllegalArgumentException
          | InvocationTargetException e)
          {
          // TODO Auto-generated catch block
          throw new RuntimeException("获取"+propDesc.getName()+"错误");
          } */
        try
        {
            Method methodSet= propDesc.getWriteMethod();
            if(methodSet !=null)
            {
                methodSet.invoke(obj,value);
            }

        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e)
        {
            // TODO Auto-generated catch block
            throw new RuntimeException("获取"+propDesc.getName()+"错误");
        }
    }

    //------------------select 结束------------------

    // update Person set age='' where id=4;

    /**
     *   设置id= id的对象 ， 他的年龄为。。。 也可以名字 ，具体修改update 语句
     * @param clazz
     * @param newValue    设置年龄的新值
     * @param id          设置某个id
     * @throws SQLException
     */
    public static void update(Class clazz,Object newValue,int id) throws SQLException
    {
        BeanInfo beanInfo = null;
        beanInfo = getBeanInfo(clazz);

        //用于数据库表
        String className = clazz.getSimpleName();

        PropertyDescriptor[] pro = beanInfo.getPropertyDescriptors();
        List<String> listFieldName = new ArrayList<String>();
        for(PropertyDescriptor temp:pro)
        {
            String propName = temp.getName();
            if(!propName.equals("id") && !propName.equals("class"))
            {
                //扣除 id和class这两个内部字段
                listFieldName.add(propName);
            }
        }

        StringBuilder sbSQL = new StringBuilder();

        //第一步 拼接  insert
        //可以设置 update 那个字符串  比如 String temp = "age"
        /**
         * 这一步写死了 不太好
         */
        sbSQL.append("update ").append(className).append(" set age = ").append(newValue).append(""
                + " where id = ?");
        JDBCUtil.executeUpdate(sbSQL.toString(), id);
    }

}
