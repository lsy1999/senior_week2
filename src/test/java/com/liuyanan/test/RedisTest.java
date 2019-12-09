package com.liuyanan.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.liuyanan.entity.User;
import com.lyn.common.utils.DateUtil;
import com.lyn.common.utils.RandomUtil;
import com.lyn.common.utils.StringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-redis.xml")
public class RedisTest {

	@Resource
	private RedisTemplate<String, User> redisTemplate;
	
	@Test
	public void testJDK() {
		List<User> list = new ArrayList<User>();
		int sum=0;
		for(int i=1;i<=50000;i++) {
			User user = new User();
			
			//(1)	ID使用1-5万的顺序号
			user.setId(i);
			//(2)	姓名使用3个随机汉字模拟
			user.setName(StringUtil.randomChineseString(3));
			//(3)	性别在女和男两个值中随机
			String a[]= {"男","女"};
			user.setSex(a[RandomUtil.random(0, 1)]);
			//(4)	手机以13开头+9位随机数模拟
			user.setPhone("13"+RandomUtil.randomNumber(9));
			//(5)	邮箱以3-20个随机字母 
			//+ @qq.com  | @163.com | @sian.com | @gmail.com | @sohu.com | @hotmail.com | @foxmail.com模拟
			String b[]= {"@qq.com","@163.com","@sian.com","@gmail.com","@sohu.com","@hotmail.com","@foxmail.com"};
			user.setEmail(RandomUtil.randomString(RandomUtil.random(3, 20))+b[RandomUtil.random(0, 6)]);
			//(6)	生日要模拟18-70岁之间，即日期从1949年到2001年之间
			Date date = DateUtil.randomDate(new Date(1949, 01, 01), new Date(2001, 12, 31));
			user.setBirthday(date);
			//添加到集合中
			list.add(user);
			sum++;
		}
		//使用RedisTemplate保存上述模拟的5万个user对象到Redis
		ListOperations<String, User> opsForList = redisTemplate.opsForList();
		long t1 = System.currentTimeMillis();
		opsForList.leftPushAll("user_list_JDK", list);
		long t2 = System.currentTimeMillis();
		//(4)	保存完成后，输出系列化方式、保存数量、所耗时间三项数据
		System.out.println("系列化方式为JDK,保存数量"+sum+",所耗时间为："+(t2-t1));
	}
	
	@Test
	public void testJSON() {
		List<User> list = new ArrayList<User>();
		int sum=0;
		for(int i=1;i<=50000;i++) {
			User user = new User();
			
			//(1)	ID使用1-5万的顺序号
			user.setId(i);
			//(2)	姓名使用3个随机汉字模拟
			user.setName(StringUtil.randomChineseString(3));
			//(3)	性别在女和男两个值中随机
			String a[]= {"男","女"};
			user.setSex(a[RandomUtil.random(0, 1)]);
			//(4)	手机以13开头+9位随机数模拟
			user.setPhone("13"+RandomUtil.randomNumber(9));
			//(5)	邮箱以3-20个随机字母 
			//+ @qq.com  | @163.com | @sian.com | @gmail.com | @sohu.com | @hotmail.com | @foxmail.com模拟
			String b[]= {"@qq.com","@163.com","@sian.com","@gmail.com","@sohu.com","@hotmail.com","@foxmail.com"};
			user.setEmail(RandomUtil.randomString(RandomUtil.random(3, 20))+b[RandomUtil.random(0, 6)]);
			//(6)	生日要模拟18-70岁之间，即日期从1949年到2001年之间
			Date date = DateUtil.randomDate(new Date(1949, 01, 01), new Date(2001, 12, 31));
			user.setBirthday(date);
			//添加到集合中
			list.add(user);
			sum++;
		}
		//使用RedisTemplate保存上述模拟的5万个user对象到Redis
		ListOperations<String, User> opsForList = redisTemplate.opsForList();
		long t1 = System.currentTimeMillis();
		opsForList.leftPushAll("user_list_JSON", list);
		long t2 = System.currentTimeMillis();
		//(4)	保存完成后，输出系列化方式、保存数量、所耗时间三项数据
		System.out.println("系列化方式为JSON,保存数量"+sum+",所耗时间为："+(t2-t1));
	}
	
	//使用RedisTemplate以hash类型保存上述模拟的5万个user对象到Redis
	@Test
	public void testHash() {
		Map<String,User> map = new HashMap<String, User>();
		int sum=0;
		for(int i=1;i<=50000;i++) {
			User user = new User();
			
			//(1)	ID使用1-5万的顺序号
			user.setId(i);
			//(2)	姓名使用3个随机汉字模拟
			user.setName(StringUtil.randomChineseString(3));
			//(3)	性别在女和男两个值中随机
			String a[]= {"男","女"};
			user.setSex(a[RandomUtil.random(0, 1)]);
			//(4)	手机以13开头+9位随机数模拟
			user.setPhone("13"+RandomUtil.randomNumber(9));
			//(5)	邮箱以3-20个随机字母 
			//+ @qq.com  | @163.com | @sian.com | @gmail.com | @sohu.com | @hotmail.com | @foxmail.com模拟
			String b[]= {"@qq.com","@163.com","@sian.com","@gmail.com","@sohu.com","@hotmail.com","@foxmail.com"};
			user.setEmail(RandomUtil.randomString(RandomUtil.random(3, 20))+b[RandomUtil.random(0, 6)]);
			//(6)	生日要模拟18-70岁之间，即日期从1949年到2001年之间
			Date date = DateUtil.randomDate(new Date(1949, 01, 01), new Date(2001, 12, 31));
			user.setBirthday(date);
			//添加到集合中
			map.put(i+"", user);
			sum++;
		}
		HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
		long t1 = System.currentTimeMillis();
		opsForHash.putAll("user_hash", map);
		long t2 = System.currentTimeMillis();
		//(4)	保存完成后，输出系列化方式、保存数量、所耗时间三项数据
		System.out.println("系列化方式为Hash,保存数量"+sum+",所耗时间为："+(t2-t1));
	}
}
