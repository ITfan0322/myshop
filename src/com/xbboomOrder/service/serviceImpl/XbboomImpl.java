package com.xbboomOrder.service.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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
import com.xbboomOrder.dao.XbboomDao;
import com.xbboomOrder.service.XbboomService;

@Service
public class XbboomImpl implements XbboomService{
	private XbboomDao dao;

	public XbboomDao getDao() {
		return dao;
	}
	@Resource
	public void setDao(XbboomDao dao) {
		this.dao = dao;
	}
	@Override
	public int test() {
		// TODO Auto-generated method stub
		return dao.test();
	}
	@Override
	public List typeList(Map map) {
		// TODO Auto-generated method stub
		return dao.typeList(map);
	}
	@Override
	public Menus findMuneById(int id) {
		// TODO Auto-generated method stub
		return dao.findMuneById(id);
	}
	@Override
	public int insertOrder(Orders orders) {
		// TODO Auto-generated method stub
		return dao.insertOrder(orders);
	}
	@Override
	public List findOrderByOpenid(String openid) {
		// TODO Auto-generated method stub
		return dao.findOrderByOpenid(openid);
	}
	@Override
	public int insertUser(Users user) {
		// TODO Auto-generated method stub
		return dao.insertUser(user);
	}
	@Override
	public int updateUserRz(Users user) {
		// TODO Auto-generated method stub
		return dao.updateUserRz(user);
	}
	@Override
	public int updateUserInfo(Users user) {
		// TODO Auto-generated method stub
		return dao.updateUserInfo(user);
	}
	@Override
	public Users findUserByOpenid(String openid) {
		// TODO Auto-generated method stub
		return dao.findUserByOpenid(openid);
	}
	@Override
	public String findMoney(String money) {
		// TODO Auto-generated method stub
		return dao.findMoney(money);
	}
	@Override
	public int updateMoney(Map map) {
		// TODO Auto-generated method stub
		return dao.updateMoney(map);
	}
	@Override
	public List findAllOrders() {
		// TODO Auto-generated method stub
		return dao.findAllOrders();
	}
	@Override
	public int updateState(Orders orders) {
		// TODO Auto-generated method stub
		return dao.updateState(orders);
	}
	@Override
	public List findAllUser() {
		// TODO Auto-generated method stub
		return dao.findAllUser();
	}
	@Override
	public int inMoney(Users user) {
		// TODO Auto-generated method stub
		return dao.inMoney(user);
	}
	@Override
	public int updateUserState(Users user) {
		// TODO Auto-generated method stub
		return dao.updateUserState(user);
	}
	@Override
	public int updateHyType(Users user) {
		// TODO Auto-generated method stub
		return dao.updateHyType(user);
	}
	@Override
	public Admin findByPhone(Admin admin) {
		// TODO Auto-generated method stub
		return dao.findByPhone(admin);
	}
	@Override
	public int insertAdmin(Admin admin) {
		// TODO Auto-generated method stub
		return dao.insertAdmin(admin);
	}
	@Override
	public int updateAdminPwd(Admin admin) {
		// TODO Auto-generated method stub
		return dao.updateAdminPwd(admin);
	}
	@Override
	public List findAllAmin() {
		// TODO Auto-generated method stub
		return dao.findAllAmin();
	}
	@Override
	public Admin findAminByPhone(String phone) {
		// TODO Auto-generated method stub
		return dao.findAminByPhone(phone);
	}
	@Override
	public List fenyeOrder(Map map) {
		// TODO Auto-generated method stub
		return dao.fenyeOrder(map);
	}
	@Override
	public int countOrder(String name) {
		// TODO Auto-generated method stub
		return dao.countOrder(name);
	}
	@Override
	public List fenyeUser(Map map) {
		// TODO Auto-generated method stub
		return dao.fenyeUser(map);
	}
	@Override
	public int countUser() {
		// TODO Auto-generated method stub
		return dao.countUser();
	}
	@Override
	public Orders findOrderById(int id) {
		// TODO Auto-generated method stub
		return dao.findOrderById(id);
	}
	@Override
	public int insertMenu(Menus menus) {
		// TODO Auto-generated method stub
		return dao.insertMenu(menus);
	}
	@Override
	public List fenyeMenu(Map map) {
		// TODO Auto-generated method stub
		return dao.fenyeMenu(map);
	}
	@Override
	public int countMenu(Map map) {
		// TODO Auto-generated method stub
		return dao.countMenu(map);
	}
	@Override
	public int delMenu(int id) {
		// TODO Auto-generated method stub
		return dao.delMenu(id);
	}
	@Override
	public List fenyeAdmin(Map map) {
		// TODO Auto-generated method stub
		return dao.fenyeAdmin(map);
	}
	@Override
	public int countAdmin() {
		// TODO Auto-generated method stub
		return dao.countAdmin();
	}
	@Override
	public int delAdmin(int id) {
		// TODO Auto-generated method stub
		return dao.delAdmin(id);
	}
	@Override
	public List<Types> findTypes() {
		// TODO Auto-generated method stub
		return dao.findTypes();
	}
	@Override
	public int updateMenu(Menus menus) {
		// TODO Auto-generated method stub
		return dao.updateMenu(menus);
	}
	@Override
	public int insertYh(Youhui youhui) {
		// TODO Auto-generated method stub
		return dao.insertYh(youhui);
	}
	@Override
	public int delYh(int id) {
		// TODO Auto-generated method stub
		return dao.delYh(id);
	}
	@Override
	public List<Youhui> findYhList(String openid) {
		// TODO Auto-generated method stub
		return dao.findYhList(openid);
	}
	@Override
	public int updateYhState(Youhui youhui) {
		// TODO Auto-generated method stub
		return dao.updateYhState(youhui);
	}
	@Override
	public List findMenuByName(String name) {
		// TODO Auto-generated method stub
		return dao.findMenuByName(name);
	}
	@Override
	public int updateMenuImg(Menus menus) {
		// TODO Auto-generated method stub
		return dao.updateMenuImg(menus);
	}
	@Override
	public List searchMenu(Map map) {
		// TODO Auto-generated method stub
		return dao.searchMenu(map);
	}
	@Override
	public int countMenus(String name) {
		// TODO Auto-generated method stub
		return dao.countMenus(name);
	}
	@Override
	public int updateNews(Users users) {
		// TODO Auto-generated method stub
		return dao.updateNews(users);
	}
	@Override
	public int insertHelps(Helps helps) {
		// TODO Auto-generated method stub
		return dao.insertHelps(helps);
	}
	@Override
	public int countHelps(Helps helps) {
		// TODO Auto-generated method stub
		return dao.countHelps(helps);
	}
	@Override
	public int countMyHelps(Helps helps) {
		// TODO Auto-generated method stub
		return dao.countMyHelps(helps);
	}
	@Override
	public int insertShops(Shops shops) {
		// TODO Auto-generated method stub
		return dao.insertShops(shops);
	}
	@Override
	public List findShop() {
		// TODO Auto-generated method stub
		return dao.findShop();
	}
	@Override
	public int delShops(int id) {
		// TODO Auto-generated method stub
		return dao.delShops(id);
	}
	@Override
	public int insertSwiper(String img) {
		// TODO Auto-generated method stub
		return dao.insertSwiper(img);
	}
	@Override
	public List findSwiper() {
		// TODO Auto-generated method stub
		return dao.findSwiper();
	}
	@Override
	public int delSwiper(int id) {
		// TODO Auto-generated method stub
		return dao.delSwiper(id);
	}
	@Override
	public int countType(Map map) {
		// TODO Auto-generated method stub
		return dao.countType(map);
	}
	@Override
	public int updateImgs(Menus menus) {
		// TODO Auto-generated method stub
		return dao.updateImgs(menus);
	}
	@Override
	public int insertAddr(com.xbboomOrder.bean.Address address) {
		// TODO Auto-generated method stub
		return dao.insertAddr(address);
	}
	@Override
	public int delAddr(int id) {
		// TODO Auto-generated method stub
		return dao.delAddr(id);
	}
	@Override
	public int updateAddr(com.xbboomOrder.bean.Address address) {
		// TODO Auto-generated method stub
		return dao.updateAddr(address);
	}
	@Override
	public List findAddr(String openid) {
		// TODO Auto-generated method stub
		return dao.findAddr(openid);
	}
	@Override
	public int insertCar(Mycar mycar) {
		// TODO Auto-generated method stub
		return dao.insertCar(mycar);
	}
	@Override
	public List findCar(String openid) {
		// TODO Auto-generated method stub
		return dao.findCar(openid);
	}
	@Override
	public Mycar jianceCar(Mycar mycar) {
		// TODO Auto-generated method stub
		return dao.jianceCar(mycar);
	}
	@Override
	public int delCar(int id) {
		// TODO Auto-generated method stub
		return dao.delCar(id);
	}
	@Override
	public int updateCarNum(Mycar mycar) {
		// TODO Auto-generated method stub
		return dao.updateCarNum(mycar);
	}
	@Override
	public Mycar findMyCarById(int id) {
		// TODO Auto-generated method stub
		return dao.findMyCarById(id);
	}
	@Override
	public com.xbboomOrder.bean.Address findAddrByIs(com.xbboomOrder.bean.Address address) {
		// TODO Auto-generated method stub
		return dao.findAddrByIs(address);
	}
	@Override
	public int changeAddrIs(int id) {
		// TODO Auto-generated method stub
		return dao.changeAddrIs(id);
	}
	@Override
	public int noAddrIs(String openid) {
		// TODO Auto-generated method stub
		return dao.noAddrIs(openid);
	}
	@Override
	public int updatePeiSong(Orders orders) {
		// TODO Auto-generated method stub
		return dao.updatePeiSong(orders);
	}
	@Override
	public List findOrdersByPphone(String pphone) {
		// TODO Auto-generated method stub
		return dao.findOrdersByPphone(pphone);
	}
	@Override
	public int updateShops(Orders orders) {
		// TODO Auto-generated method stub
		return dao.updateShops(orders);
	}
	@Override
	public int updateTypes(Types types) {
		// TODO Auto-generated method stub
		return dao.updateTypes(types);
	}
	@Override
	public int updateGg(Map map) {
		// TODO Auto-generated method stub
		return dao.updateGg(map);
	}
	@Override
	public Map findGg() {
		// TODO Auto-generated method stub
		return dao.findGg();
	}
	@Override
	public int updateMenuType(Map map) {
		// TODO Auto-generated method stub
		return dao.updateMenuType(map);
	}
	@Override
	public Types findTypeById(int id) {
		// TODO Auto-generated method stub
		return dao.findTypeById(id);
	}
	@Override
	public int updateMeneStatus(Menus menus) {
		// TODO Auto-generated method stub
		return dao.updateMeneStatus(menus);
	}
	@Override
	public int insertTypes(Types types) {
		// TODO Auto-generated method stub
		return dao.insertTypes(types);
	}
	@Override
	public int updateTypesImg(Types types) {
		// TODO Auto-generated method stub
		return dao.updateTypesImg(types);
	}
	@Override
	public int delTypes(int id) {
		// TODO Auto-generated method stub
		return dao.delTypes(id);
	}
	@Override
	public int updateAllMenuType(Menus menus) {
		// TODO Auto-generated method stub
		return dao.updateAllMenuType(menus);
	}
	@Override
	public int updateSx(Map map) {
		// TODO Auto-generated method stub
		return dao.updateSx(map);
	}
	@Override
	public String findOrderStatus(String ordernum) {
		// TODO Auto-generated method stub
		return dao.findOrderStatus(ordernum);
	}
	@Override
	public Shops findShopById(String openid) {
		// TODO Auto-generated method stub
		return dao.findShopById(openid);
	}
	@Override
	public Shops findShopByNum(String number) {
		// TODO Auto-generated method stub
		return dao.findShopByNum(number);
	}
	@Override
	public int updateShopsByNum(Shops shops) {
		// TODO Auto-generated method stub
		return dao.updateShopsByNum(shops);
	}
	@Override
	public int insertShoptype(Shoptype shoptype) {
		// TODO Auto-generated method stub
		return dao.insertShoptype(shoptype);
	}
	@Override
	public int delShopType(int id) {
		// TODO Auto-generated method stub
		return dao.delShopType(id);
	}
	@Override
	public int updateShoptype(Shoptype shoptype) {
		// TODO Auto-generated method stub
		return dao.updateShoptype(shoptype);
	}
	@Override
	public List<Shoptype> findShopType(String number) {
		// TODO Auto-generated method stub
		return dao.findShopType(number);
	}
	@Override
	public int insertShopMenu(ShopMenu shopMenu) {
		// TODO Auto-generated method stub
		return dao.insertShopMenu(shopMenu);
	}
	@Override
	public List findShopMenu(Map map) {
		// TODO Auto-generated method stub
		return dao.findShopMenu(map);
	}
	@Override
	public int countShopMenu(Map map) {
		// TODO Auto-generated method stub
		return dao.countShopMenu(map);
	}
	@Override
	public int updateShopMenuStatus(ShopMenu shopMenu) {
		// TODO Auto-generated method stub
		return dao.updateShopMenuStatus(shopMenu);
	}
	@Override
	public int updateShopMenuKucun(Map map) {
		// TODO Auto-generated method stub
		return dao.updateShopMenuKucun(map);
	}
	@Override
	public int delShopMenu(int id) {
		// TODO Auto-generated method stub
		return dao.delShopMenu(id);
	}
	@Override
	public ShopMenu findShopMenuByMid(ShopMenu shopMenu) {
		// TODO Auto-generated method stub
		return dao.findShopMenuByMid(shopMenu);
	}
	@Override
	public List<Orders> findOrderByNum(String num) {
		// TODO Auto-generated method stub
		return dao.findOrderByNum(num);
	}
	@Override
	public List findShopMenuByName(ShopMenu shopMenu) {
		// TODO Auto-generated method stub
		return dao.findShopMenuByName(shopMenu);
	}
	@Override
	public Menus findMenuById(int id) {
		// TODO Auto-generated method stub
		return dao.findMenuById(id);
	}
	@Override
	public int updateMenuNum(Map map) {
		// TODO Auto-generated method stub
		return dao.updateMenuNum(map);
	}
	@Override
	public Users findUserByTjm(String tjm) {
		// TODO Auto-generated method stub
		return dao.findUserByTjm(tjm);
	}
	@Override
	public int updateGrade(Users users) {
		// TODO Auto-generated method stub
		return dao.updateGrade(users);
	}
	@Override
	public Fenxiao findFenxiao() {
		// TODO Auto-generated method stub
		return dao.findFenxiao();
	}
	@Override
	public int updateFenxiao(Fenxiao fenxiao) {
		// TODO Auto-generated method stub
		return dao.updateFenxiao(fenxiao);
	}
	@Override
	public List findTuanDui(String toptjm) {
		// TODO Auto-generated method stub
		return dao.findTuanDui(toptjm);
	}
	@Override
	public int editShopMenu(ShopMenu shopMenu) {
		// TODO Auto-generated method stub
		return dao.editShopMenu(shopMenu);
	}
	@Override
	public ShopMenu findKucun(int id) {
		// TODO Auto-generated method stub
		return dao.findKucun(id);
	}
	@Override
	public Mycar findCarNum(int id) {
		// TODO Auto-generated method stub
		return dao.findCarNum(id);
	}
	@Override
	public int updateStates(Orders orders) {
		// TODO Auto-generated method stub
		return dao.updateStates(orders);
	}
	@Override
	public List fenyeShop(Map map) {
		// TODO Auto-generated method stub
		return dao.fenyeShop(map);
	}
	@Override
	public int countShop(Map map) {
		// TODO Auto-generated method stub
		return dao.countShop(map);
	}
	@Override
	public int updateShopState(Shops shops) {
		// TODO Auto-generated method stub
		return dao.updateShopState(shops);
	}
	@Override
	public int updateToptjm(Users users) {
		// TODO Auto-generated method stub
		return dao.updateToptjm(users);
	}
	@Override
	public List fenyeGhShop(Map map) {
		// TODO Auto-generated method stub
		return dao.fenyeGhShop(map);
	}
	@Override
	public int countGhshop(Map map) {
		// TODO Auto-generated method stub
		return dao.countGhshop(map);
	}
	@Override
	public int insertGhShop(GhShop ghShop) {
		// TODO Auto-generated method stub
		return dao.insertGhShop(ghShop);
	}
	@Override
	public int updateGhShopStatus(GhShop ghShop) {
		// TODO Auto-generated method stub
		return dao.updateGhShopStatus(ghShop);
	}
	@Override
	public GhShop findGhShop(GhShop ghShop) {
		// TODO Auto-generated method stub
		return dao.findGhShop(ghShop);
	}
	@Override
	public Map findGrade() {
		// TODO Auto-generated method stub
		return dao.findGrade();
	}
	@Override
	public int updateMGrade(Map map) {
		// TODO Auto-generated method stub
		return dao.updateMGrade(map);
	}
	@Override
	public GhShop findGhShopByPhone(String phone) {
		// TODO Auto-generated method stub
		return dao.findGhShopByPhone(phone);
	}
	@Override
	public Shops findShopsByPhoneAndNumber(Shops shops) {
		// TODO Auto-generated method stub
		return dao.findShopsByPhoneAndNumber(shops);
	}
	@Override
	public int updateMoneys(Map map) {
		// TODO Auto-generated method stub
		return dao.updateMoneys(map);
	}
	@Override
	public List findMyAllOrders(String number) {
		// TODO Auto-generated method stub
		return dao.findMyAllOrders(number);
	}
	@Override
	public int insertCash(Cash cash) {
		// TODO Auto-generated method stub
		return dao.insertCash(cash);
	}
	@Override
	public int updateCashState(Cash cash) {
		// TODO Auto-generated method stub
		return dao.updateCashState(cash);
	}
	@Override
	public List<Cash> findCashList(Map map) {
		// TODO Auto-generated method stub
		return dao.findCashList(map);
	}
	@Override
	public int countCash(Map map) {
		// TODO Auto-generated method stub
		return dao.countCash(map);
	}
	@Override
	public List findAllGhShop() {
		// TODO Auto-generated method stub
		return dao.findAllGhShop();
	}
	@Override
	public int insertFenxiao(Fenxiao fenxiao) {
		// TODO Auto-generated method stub
		return dao.insertFenxiao(fenxiao);
	}
	@Override
	public Fenxiao findFenxiaoById(int id) {
		// TODO Auto-generated method stub
		return dao.findFenxiaoById(id);
	}
	@Override
	public List typeLists(Map map) {
		// TODO Auto-generated method stub
		return dao.typeLists(map);
	}
	@Override
	public int countTypes(Map map) {
		// TODO Auto-generated method stub
		return dao.countTypes(map);
	}
	
}
