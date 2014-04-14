public interface Protocol {
	//聊天内容如果发送与协议相同的字符串会响应相应的事件，因此协议的字符串越复杂越好，这只是个简单示例
	
	final String CLIENT_EXIT = new String("天王盖地虎");

	final String SERVER_EXIT = new String("宝塔镇河妖");

	final String USERNAME = new String("这是用户名");

	final String USERNAME_EXIST = new String("用户名已经存在");

	final String CONNECT_OK = new String("连接成功");
	
	final String USERS_LIST = new String("83175815389138598");
	
	final String TO_SOMEBODY = new String("dkajldfjalk;j");
	
	//注意各个协议的使用方法不要相互影响！！本程序是将协议加在数据后面
}
