package com.xbboomOrder.dao;

import java.util.List;
import java.util.Map;

import javax.xml.registry.infomodel.User;

import org.springframework.stereotype.Repository;

import com.sun.glass.ui.Menu;
import com.sun.jndi.cosnaming.IiopUrl.Address;
import com.xbboomOrder.bean.Admin;
import com.xbboomOrder.bean.Cash;
import com.xbboomOrder.bean.Fenxiao;
import com.xbboomOrder.bean.GhShop;
import com.xbboomOrder.bean.Helps;
import com.xbboomOrder.bean.Menus;
import com.xbboomOrder.bean.Mycar;
import com.xbboomOrder.bean.Orders;
import com.xbboomOrder.bean.ShopMenu;
import com.xbboomOrder.bean.Shops;
import com.xbboomOrder.bean.Shoptype;
import com.xbboomOrder.bean.Types;
import com.xbboomOrder.bean.Users;
import com.xbboomOrder.bean.Youhui;

@Repository
public interface XbboomDao {
	public int test();

	// 菜单列表
	public List typeList(Map map);

	// 统计菜单
	public int countType(Map map);
	// 菜单列表
		public List typeLists(Map map);

		// 统计菜单
		public int countTypes(Map map);
	// ����id��ѯ���
	public Menus findMuneById(int id);

	// �����¶���
	public int insertOrder(Orders orders);

	// ����openid��ѯ����
	public List findOrderByOpenid(String openid);

	// ��ѯ�û���Ϣ
	public Users findUserByOpenid(String openid);

	// �������û�
	public int insertUser(Users user);

	// ������֤
	public int updateUserRz(Users user);

	// ������ϵ��ʽ
	public int updateUserInfo(Users user);

	// ��ѯ���
	public String findMoney(String openid);

	// �޸����
	public int updateMoney(Map map);

	// ��ѯ���ж���
	public List findAllOrders();

	// �޸�״̬
	public int updateState(Orders orders);

	// ��ѯ�����û�
	public List findAllUser();

	// D�ҳ�ֵ
	public int inMoney(Users user);

	// �޸ĺ�Լ״̬
	public int updateUserState(Users user);

	// ԤԼ״̬�޸�
	public int updateHyType(Users user);

	// 登录验证
	public Admin findByPhone(Admin admin);

	// 添加人员
	public int insertAdmin(Admin admin);

	// 修改密码
	public int updateAdminPwd(Admin admin);

	// 查询所有
	public List findAllAmin();

	// 根据电话查询
	public Admin findAminByPhone(String phone);

	// 分页查新订单
	public List fenyeOrder(Map map);

	// 统计订单
	public int countOrder(String name);

	// 分页查询用户
	public List fenyeUser(Map map);

	// 统计订单
	public int countUser();

	// 根据id查询订单
	public Orders findOrderById(int id);

	// 添加商品
	public int insertMenu(Menus menus);

	// 分页查询菜单
	public List fenyeMenu(Map map);

	// 统计菜单
	public int countMenu(Map map);

	// 删除商品
	public int delMenu(int id);

	// 分页查询管理员
	public List fenyeAdmin(Map map);

	// 统计菜单
	public int countAdmin();

	// 删除管理员
	public int delAdmin(int id);

	// 查询分类
	public List<Types> findTypes();

	// 修改商品信息
	public int updateMenu(Menus menus);

	// 添加优惠券
	public int insertYh(Youhui youhui);

	// 删除优惠券
	public int delYh(int id);

	// 根据openid查询优惠券
	public List<Youhui> findYhList(String openid);

	// 修改优惠券使用状态
	public int updateYhState(Youhui youhui);

	// 模糊查询

	public List findMenuByName(String name);

	// 修改商品图片
	public int updateMenuImg(Menus menus);

	// 后台查询商品
	public List searchMenu(Map map);

	// 后台查询商品
	public int countMenus(String name);

	// 修改新用户状态
	public int updateNews(Users users);

	// 添加助力
	public int insertHelps(Helps helps);

	// 查询助力
	public int countHelps(Helps helps);

	// 查询助力
	public int countMyHelps(Helps helps);

	// 插入店铺
	public int insertShops(Shops shops);

	// 查询店铺
	public List findShop();

	// 删除店铺
	public int delShops(int id);

	// 插入轮播图
	public int insertSwiper(String img);

	// 查询轮播图
	public List findSwiper();

	// 删除轮播图
	public int delSwiper(int id);

	// 修改商品详情
	public int updateImgs(Menus menus);

	// 添加地址
	public int insertAddr(com.xbboomOrder.bean.Address address);

	// 删除地址
	public int delAddr(int id);

	// 修改地址
	public int updateAddr(com.xbboomOrder.bean.Address address);

	// 查询地址
	public List findAddr(String openid);

	// 查询地址根据选择
	public com.xbboomOrder.bean.Address findAddrByIs(com.xbboomOrder.bean.Address address);

	// 更改默认地址
	public int changeAddrIs(int id);

	// 地址全部未选择
	public int noAddrIs(String openid);

	// 添加购物车
	public int insertCar(Mycar mycar);

	// 查询购物车
	public List findCar(String openid);

	// 检测购物车
	public Mycar jianceCar(Mycar mycar);

	// 删除购物车
	public int delCar(int id);

	// 更改购物车数量
	public int updateCarNum(Mycar mycar);

	// 根据id查询购物车
	public Mycar findMyCarById(int id);

	// 修改配送员
	public int updatePeiSong(Orders orders);

	// 配送员查询订单
	public List findOrdersByPphone(String pphone);

	// 修改店铺
	public int updateShops(Orders orders);

	// 修改分类
	public int updateTypes(Types types);

	// 添加分类
	public int insertTypes(Types types);
	
	// 修改分类图标
	public int updateTypesImg(Types types);

	// 修改公告
	public int updateGg(Map map);

	// 查询公告
	public Map findGg();

	// 更改全部分类
	public int updateMenuType(Map map);

	// 根据id查询分类
	public Types findTypeById(int id);

	// 修改商品下架
	public int updateMeneStatus(Menus menus);
	
	//删除分类
	public int delTypes(int id) ;
	
	// 更改全部分类
	public int updateAllMenuType(Menus menus);
	
	// 排序
	public int updateSx(Map map);
	
	public String findOrderStatus(String ordernum);
	
	//查询个人店铺
	public Shops findShopById(String openid);
	
	//查询个人店铺
	public Shops findShopByNum(String number);
	
	//店铺认证信息
	public int updateShopsByNum(Shops shops);
	
	//添加店铺分类
	public int insertShoptype(Shoptype shoptype);
	
	//删除店铺分类
	public int delShopType(int id);
	
	//修改店铺分类
	public int updateShoptype(Shoptype shoptype);
	
	//查询店铺分类
	public List<Shoptype> findShopType(String number);
	
	//添加店铺商品
	public int insertShopMenu(ShopMenu shopMenu);
	
	//查询店铺商品
	public List findShopMenu(Map map);
	
	//统计店铺商品
	public int countShopMenu(Map map);
	
	//上架下架
	public int updateShopMenuStatus(ShopMenu shopMenu);
	
	//修改库存
	public int updateShopMenuKucun(Map map);
	
	//删除商品
	public int delShopMenu(int id);
	
	//根据商品id查询商品
	public ShopMenu findShopMenuByMid(ShopMenu shopMenu);
	
	//根据商品名称查询商品
	public List findShopMenuByName(ShopMenu shopMenu);
		
	
	//根据订单号查询订单
	public List<Orders> findOrderByNum(String num);
	
	//根据id查询商品
	public Menus findMenuById(int id);
	
	//修改商品销量
	public int updateMenuNum(Map map);
	
	//根据推荐码查询用户
	public Users findUserByTjm(String tjm);
	
	//修改用户等级
	public int updateGrade(Users users);
	
	//查询分销信息；
	public Fenxiao findFenxiao();
	
	//查询等级信息；
	public Map findGrade();
	
	//修改分销
	public int updateFenxiao(Fenxiao fenxiao);
	
	//修改等级
	public int updateMGrade(Map map);
	
	//我的团队
	public List findTuanDui(String toptjm);
	
	//编辑商品信息
	public int editShopMenu(ShopMenu shopMenu);
	
	//查询库存
	public ShopMenu findKucun(int id);
	
	
	//查询购物车商品数量
	public Mycar findCarNum(int id);
	
	//修改订单状态
	public int updateStates(Orders orders);
	
	//分页查询店铺
	public List fenyeShop(Map map);
	
	//分页查询店铺
	public int countShop(Map map);
	
	//审核店铺
	public int updateShopState(Shops shops);
	
	//修改上级推荐码
	public int updateToptjm(Users users);
	
	//分页查询供货商
	public List fenyeGhShop(Map map);
	
	//统计供货商
	public int countGhshop(Map map);
	
	//供货商注册
	public int insertGhShop(GhShop ghShop);
	
	//审核供货商
	public int updateGhShopStatus(GhShop ghShop);
	
	//供货商登录
	public GhShop findGhShop(GhShop ghShop);
	
	//判断品牌商是否存在
	public GhShop findGhShopByPhone(String phone);
	
	//店铺登录
	public Shops findShopsByPhoneAndNumber(Shops shops);
	
	//修改钱包
	public int updateMoneys(Map map);
	
	//查询所有商品订单
	public List findMyAllOrders(String number);
	
	//申请提现
	public int insertCash(Cash cash);
	
	//修改提现状态
	public int updateCashState(Cash cash);
	
	//查询提现
	public List<Cash> findCashList(Map map);
	
	//统计提现
	public int countCash(Map map);
	
	//查询所有品牌商
	public List findAllGhShop();
	
	//添加分销
	public int insertFenxiao(Fenxiao fenxiao);
	
	//根绝id查询分销
	public Fenxiao findFenxiaoById(int id);
	
	
}
