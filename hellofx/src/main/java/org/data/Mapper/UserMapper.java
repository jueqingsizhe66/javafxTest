package org.data.Mapper;

import org.apache.ibatis.annotations.Param;
import org.data.Entity2.User;

public interface UserMapper {
    public User selectById(int uid);
    public boolean addUser(User user);
    public boolean updateUser(User user);
    public boolean deleteById(int uid);

    public User selectByIdAndUsername(@Param("uid") int uid, @Param("uname") String uname);
}
