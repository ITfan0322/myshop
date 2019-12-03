package com.xbboomOrder.controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.JDOMException;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.google.gson.Gson;
import com.sun.corba.se.spi.orb.StringPair;
import com.xbboomOrder.bean.Address;
import com.xbboomOrder.bean.Admin;
import com.xbboomOrder.bean.Cash;
import com.xbboomOrder.bean.Fenxiao;
import com.xbboomOrder.bean.GhShop;
import com.xbboomOrder.bean.Helps;
import com.xbboomOrder.bean.Menus;
import com.xbboomOrder.bean.Mycar;
import com.xbboomOrder.bean.Orders;
import com.xbboomOrder.bean.PayDetailBean;
import com.xbboomOrder.bean.ShopMenu;
import com.xbboomOrder.bean.Shops;
import com.xbboomOrder.bean.Shoptype;
import com.xbboomOrder.bean.Types;
import com.xbboomOrder.bean.Users;
import com.xbboomOrder.bean.Youhui;
import com.xbboomOrder.refound.WXPayRefundUtil;
import com.xbboomOrder.scan.Scans;
import com.xbboomOrder.service.XbboomService;
import com.xbboomOrder.sms.SmsMultiSender;
import com.xbboomOrder.sms.SmsMultiSenderResult;
import com.xbboomOrder.utils.CollectionUtil;
import com.xbboomOrder.utils.FileUploadUtils;
import com.xbboomOrder.utils.GetOpenid;
import com.xbboomOrder.utils.GetToken;
import com.xbboomOrder.utils.HttpUtil;
import com.xbboomOrder.utils.HttpUtils;
import com.xbboomOrder.utils.JsonResult;
import com.xbboomOrder.utils.PayCommonUtil;
import com.xbboomOrder.utils.PayUtil;
import com.xbboomOrder.utils.Randem;
import com.xbboomOrder.utils.ResponseData;
import com.xbboomOrder.utils.SerializerFeatureUtil;
import com.xbboomOrder.utils.SignUtils;
import com.xbboomOrder.utils.WebUtil;
import com.xbboomOrder.utils.XMLUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class XbboomController {
	private XbboomService services;
	String url = "https://pai.xbboom.com";
	// String url = "http://192.168.0.103:8080";
	String ordernum = "";

	public XbboomService getServices() {
		return services;
	}

	@Resource
	public void setServices(XbboomService services) {
		this.services = services;
	}

	// 获取微信信息
	@ResponseBody
	@RequestMapping("getUserInfo")
	public void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String js_code = request.getParameter("js_code");
		String encryptedData = request.getParameter("encryptedData");
		String iv = request.getParameter("iv");
		String sessionkey = new GetOpenid().get(js_code);
		System.out.println(sessionkey);
		JSONObject obj = new GetOpenid().getUserInfo(encryptedData, sessionkey, iv);
		System.out.println(obj);
		System.out.println("openid=" + obj.get("openId"));
		Users users = services.findUserByOpenid(obj.get("openId").toString());

		if (null == users) {
			Users user = new Users();
			user.setOpenid(obj.get("openId").toString());
			user.setAddress(obj.get("country") + "-" + obj.get("province") + "-" + obj.get("city"));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
			String date = df.format(new Date());
			user.setDates(date);
			user.setMytjm(getRandomString(8));
			user.setName(URLEncoder.encode(obj.get("nickName").toString(), "utf-8"));
			user.setImg(obj.get("avatarUrl").toString());
			services.insertUser(user);
		}
		Gson gson = new Gson();
		String json = gson.toJson(obj);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 接口测试
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("test")
	@ResponseBody
	public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String name = request.getParameter("name");
		System.out.println(name);
		Gson gson = new Gson();
		String json = gson.toJson(name);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 查询类别
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("typeList")
	@ResponseBody
	public void typeList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String types = "%" + request.getParameter("types") + "%";
		String name = "%" + request.getParameter("name") + "%";
		String px = request.getParameter("px");
		String sx = request.getParameter("sx");
		System.out.println(px);
		System.out.println(sx);
		Map m = new HashMap<>();
		m.put("types", types);
		m.put("name", name);
		int count = services.countType(m);
		System.out.println(count);
		int id = Integer.parseInt(request.getParameter("start"));
		int end = Integer.parseInt(request.getParameter("end"));
		int start = (id - 1) * end;
		int pages = 0;
		if (count % end == 0) {
			pages = count / end;
		} else {
			pages = (count / end) + 1;
		}
		Map map = new HashMap<>();
		map.put("name", name);
		map.put("types", types);
		map.put("start", start);
		map.put("end", end);
		System.out.println(types);
		List list = new ArrayList<>();
		list = services.typeList(map);
		Map maps = new HashMap<>();
		maps.put("list", list);
		maps.put("pages", pages);
		Gson gson = new Gson();
		String json = gson.toJson(maps);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}
	/**
	 * 查询类别
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("typeLists")
	@ResponseBody
	public void typeLists(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String types = "%" + request.getParameter("types") + "%";
		String name = "%" + request.getParameter("name") + "%";
		String px = request.getParameter("px");
		String sx = request.getParameter("sx");
		int ghid = Integer.parseInt(request.getParameter("ghid"));
		System.out.println(px);
		System.out.println(sx);
		Map m = new HashMap<>();
		m.put("types", types);
		m.put("name", name);
		m.put("ghid", ghid);
		int count = services.countTypes(m);
		System.out.println(count);
		int id = Integer.parseInt(request.getParameter("start"));
		int end = Integer.parseInt(request.getParameter("end"));
		int start = (id - 1) * end;
		int pages = 0;
		if (count % end == 0) {
			pages = count / end;
		} else {
			pages = (count / end) + 1;
		}
		Map map = new HashMap<>();
		map.put("name", name);
		map.put("types", types);
		map.put("start", start);
		map.put("end", end);
		map.put("ghid", ghid);
		System.out.println(types);
		List list = new ArrayList<>();
		list = services.typeLists(map);
		Map maps = new HashMap<>();
		maps.put("list", list);
		maps.put("pages", pages);
		Gson gson = new Gson();
		String json = gson.toJson(maps);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}
	/**
	 * 根据id查询类别
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("findMuneById")
	@ResponseBody
	public void findMuneById(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int id = Integer.parseInt(request.getParameter("id"));
		System.out.println(id);
		Menus menus = services.findMuneById(id);
		Gson gson = new Gson();
		String json = gson.toJson(menus);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 根据id查询类别
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("updateImgs")
	@ResponseBody
	public void updateImgs(Menus menus, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int a = services.updateImgs(menus);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 插入新订单
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("insertOrder")
	@ResponseBody
	public void insertOrder(Orders orders, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		System.out.println(orders.getBeizhu());
		int a = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String date = df.format(new Date());
		String ordernum = getRandomString(4) + (System.currentTimeMillis() / 1000);
		orders.setDates(date);
		orders.setOrdernum(ordernum);
		System.out.println(orders.getContent());
		DecimalFormat dd = new DecimalFormat("#.00");
		JSONArray json = JSONArray.fromObject(orders.getContent());
		for (int i = 0; i < json.size(); i++) {
			JSONObject j = JSONObject.fromObject(json.get(i));
			JSONObject j2 = JSONObject.fromObject(j.get("menus"));
			System.out.println("----------------------------------");
			System.out.println(j2.get("name"));
			orders.setContent(json.get(i).toString());
			orders.setPrice(dd.format((j.getInt("num") * j2.getDouble("price"))) + "");
			orders.setShops(j.get("sname").toString());
			orders.setSid(j.getString("sid"));
			services.insertOrder(orders);
		}
		Gson gson = new Gson();
		String jsons = gson.toJson(ordernum);
		PrintWriter pw = response.getWriter();
		pw.write(jsons);
		pw.close();
	}

	/**
	 * 根据openid查询订单
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("findOrderByOpenid")
	@ResponseBody
	public void findOrderByOpenid(String openid, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		List list = services.findOrderByOpenid(openid);
		Gson gson = new Gson();
		String json = gson.toJson(list);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 查询库存
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("findKucun")
	@ResponseBody
	public void findKucun(String id, String smid, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int a = 0;
		Map map = new HashMap<>();
		map.put("a", a);
		JSONArray jArray = JSONArray.fromObject(id);
		JSONArray jArray1 = JSONArray.fromObject(smid);
		System.out.println(jArray.size());
		System.out.println(jArray1.size());
		List list = new ArrayList<>();
		for (int i = 0; i < jArray.size(); i++) {
			ShopMenu menu = services.findKucun(jArray1.getInt(i));
			Mycar car = services.findCarNum(jArray.getInt(i));

			if (Integer.parseInt(menu.getKucun()) < car.getNum()) {
				JSONObject jsonObject = JSONObject.fromObject(car.getMenus());
				a = 1;
				map.put("a", a);
				list.add(jsonObject.get("name"));

			}
		}
		map.put("list", list);
		Gson gson = new Gson();
		String json = gson.toJson(map);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 插入信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("findUserByOpenid")
	@ResponseBody
	public void findUserByOpenid(String openid, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		Users user = services.findUserByOpenid(openid);
		user.setName(URLDecoder.decode(user.getName(), "utf-8"));
		Gson gson = new Gson();
		String json = gson.toJson(user);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 插入信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("findUserByTjm")
	@ResponseBody
	public void findUserByTjm(String mytjm, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		System.out.println(mytjm);
		Users user = services.findUserByTjm(mytjm);
		System.out.println(user);
		user.setName(URLDecoder.decode(user.getName(), "utf-8"));
		Gson gson = new Gson();
		String json = gson.toJson(user);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 插入信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("insertUser")
	@ResponseBody
	public void insertUser(Users user, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int a = 0;
		if (null == user.getOpenid() || "".equals(user.getOpenid())) {
			a = 2;
		} else {
			Users users = services.findUserByOpenid(user.getOpenid());
			if (null == users) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
				String date = df.format(new Date());
				user.setDates(date);
				a = services.insertUser(user);

			}
		}

		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 更新认证信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("updateUserRz")
	@ResponseBody
	public void updateUserRz(Users user, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int a = services.updateUserRz(user);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 更新基本信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("updateUserInfo")
	@ResponseBody
	public void updateUserInfo(Users user, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		System.out.println(user);
		int a = services.updateUserInfo(user);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 查询所有订单
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("findAllOrders")
	@ResponseBody
	public void findAllOrders(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		List list = services.findAllOrders();
		Gson gson = new Gson();
		String json = gson.toJson(list);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 查询所有订单
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("findMyAllOrders")
	@ResponseBody
	public void findMyAllOrders(String sid, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		List list = services.findMyAllOrders("%" + sid + "%");
		Gson gson = new Gson();
		String json = gson.toJson(list);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	public void sendSms(String[] phone, int templateId, String[] params) {
		// 短信应用SDK AppID
		int appid = 1400231519; // 1400开头

		// 短信应用SDK AppKey
		String appkey = "af2b1f3add8130eabd7bbd748784e31c";

		// 需要发送短信的手机号码
		// String[] phoneNumbers = {"17600196265"};

		// 短信模板ID，需要在短信应用中申请
		// NOTE: 这里的模板ID`7839`只是一个示例，
		// 真实的模板ID需要在短信控制台中申请
		// int templateId = 371691;

		// 签名
		// NOTE: 这里的签名"腾讯云"只是一个示例，
		// 真实的签名需要在短信控制台中申请，另外
		// 签名参数使用的是`签名内容`，而不是`签名ID`
		String smsSign = "优趣科技";

		try {
			SmsMultiSender msender = new SmsMultiSender(appid, appkey);
			SmsMultiSenderResult result = msender.sendWithParam("86", phone, templateId, params, smsSign, "", ""); // 签名参数未提供或者为空时，会使用默认签名发送短信
			System.out.println(result);
		} catch (HTTPException e) {
			// HTTP 响应码错误
			e.printStackTrace();
		} catch (JSONException e) {
			// JSON 解析错误
			e.printStackTrace();
		} catch (IOException e) {
			// 网络 IO 错误
			e.printStackTrace();
		}
	}

	/**
	 * 修改订单状态
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("updateState")
	@ResponseBody
	public void updateState(Orders orders, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int a = 0;

		System.out.println(a);
		Users users = services.findUserByOpenid(orders.getOpenid());
		double moneys = Double.parseDouble(users.getMoney());
		double price = Double.parseDouble(orders.getPrice().replaceAll("\"", ""));
		if (moneys < price) {
			a = 2;
		} else if (moneys >= price) {
			users.setMoney((moneys - price) + "");
			a = services.updateState(orders);

			int p = 0;
			while (p == 0) {
				Users users2 = services.findUserByOpenid(orders.getOpenid());
				double moneys2 = Double.parseDouble(users.getMoney());
				Map map = new HashMap<>();
				map.put("money", Double.parseDouble(users2.getMoney()) - price);
				map.put("moneys", Double.parseDouble(users2.getMoney()));
				map.put("openid", orders.getOpenid());
				p = services.updateMoneys(map);
			}
			Fenxiao fenxiao = services.findFenxiao();
			DecimalFormat df = new DecimalFormat("#.00");
			System.out.println("-----------------------");
			List<Orders> list = services.findOrderByNum(orders.getOrdernum());
			String phone[] = new String[list.size()];
			boolean flag = false;
			for (int i = 0; i < list.size(); i++) {
				Shops shops = services.findShopByNum(list.get(i).getSid());
				phone[i] = shops.getPhone();
				if (i == list.size() - 1) {
					flag = true;
				}
			}
			if (flag) {
				String[] area = { "" };
				sendSms(phone, 371691, area);
			}
			for (int i = 0; i < list.size(); i++) {
				int b = 0;
				int c = 0;
				int d = 0;
				int e = 0;

				while (b == 0) {
					JSONObject jsonObject = JSONObject.fromObject(list.get(i).getContent());
					ShopMenu shopMenu = new ShopMenu();
					shopMenu.setColors(jsonObject.getString("colors"));
					shopMenu.setMid(jsonObject.getInt("mid"));
					shopMenu.setSize(jsonObject.getString("size"));
					shopMenu.setNumber(jsonObject.getString("sid"));
					ShopMenu sMenu = services.findShopMenuByMid(shopMenu);
					Map map1 = new HashMap<>();
					map1.put("id", sMenu.getId());
					int s = 0;
					if (Integer.parseInt(sMenu.getKucun()) - jsonObject.getInt("num") <= 0) {
						s = 0;
					} else {
						s = Integer.parseInt(sMenu.getKucun()) - jsonObject.getInt("num");
					}
					map1.put("kucun", s);
					map1.put("num", sMenu.getNum() + jsonObject.getInt("num"));
					map1.put("kucuns", Integer.parseInt(sMenu.getKucun()));
					map1.put("nums", sMenu.getNum());
					b = services.updateShopMenuKucun(map1);
				}
				while (c == 0) {
					JSONObject jsonObject = JSONObject.fromObject(list.get(i).getContent());
					ShopMenu shopMenu = new ShopMenu();
					int mid = jsonObject.getInt("mid");
					Menus menu = services.findMenuById(mid);
					Map map1 = new HashMap<>();
					map1.put("id", menu.getId());
					map1.put("num", menu.getNum() + jsonObject.getInt("num"));
					map1.put("nums", menu.getNum());
					c = services.updateMenuNum(map1);
					// b = services.updateShopMenuKucun(sMenu);
				}
				if (users.getToptjm() != null && users.getToptjm() != "" && !"".equals(users.getToptjm())) {
					while (d == 0) {
						Users users1 = services.findUserByOpenid(list.get(i).getOpenid());
						Users users2 = services.findUserByTjm(users1.getToptjm());
						if (users2 != null) {

							double money = Double.parseDouble(users2.getMoney())
									+ (Double.parseDouble(list.get(i).getPrice()) * fenxiao.getOne());
							Map map1 = new HashMap<>();
							map1.put("mytjm", users2.getMytjm());
							if (Integer.parseInt(users2.getGrade()) == 1) {
								if (money >= 200) {
									map1.put("money", df.format(200));
								} else {
									map1.put("money", df.format(money));
								}
							}
							map1.put("moneys", users2.getMoney());
							d = services.updateMoney(map1);
						} else {
							d = 1;
						}
					}
					if (fenxiao.getGrade() == 2) {
						while (e == 0) {
							Users users1 = services.findUserByOpenid(list.get(i).getOpenid());
							Users users2 = services.findUserByTjm(users1.getToptjm());
							Users users3 = services.findUserByTjm(users2.getToptjm());
							if (users3 != null) {

								double money = Double.parseDouble(users3.getMoney())
										+ (Double.parseDouble(list.get(i).getPrice()) * fenxiao.getTwo());
								Map map1 = new HashMap<>();
								map1.put("mytjm", users3.getMytjm());
								if (Integer.parseInt(users3.getGrade()) == 1) {
									if (money >= 200) {
										map1.put("money", df.format(200));
									} else {
										map1.put("money", df.format(money));
									}
								}

								map1.put("moneys", users3.getMoney());
								e = services.updateMoney(map1);
							} else {
								e = 1;
							}
						}
					}
					System.out.println("付款回执：---------" + a + b + c + d + e);
				}
			}
		}
		/*
		 * int yid = Integer.parseInt(request.getParameter("yid")); Youhui youhui = new
		 * Youhui(); youhui.setId(yid); youhui.setStates(1);
		 * services.updateYhState(youhui);
		 */
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	// 提现
	@ResponseBody
	@RequestMapping("cash")
	public void cash(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String appid = "wxd3270e7769759ce7";
		String mchid = "1541631801";
		String nonce_str = System.currentTimeMillis() / 1000 + "";
		String check_name = "NO_CHECK";
		String amount = request.getParameter("money");
		String spbill_create_ip = getLocalIp(request);
		String openid = request.getParameter("openid");
		String title = request.getParameter("title");
		Map<String, String> restmap = null;
		try {
			Map<String, String> parm = new HashMap<String, String>();
			parm.put("mch_appid", appid); // 公众账号appid
			parm.put("mchid", mchid); // 商户号
			parm.put("nonce_str", PayUtil.getNonceStr()); // 随机字符串
			parm.put("partner_trade_no", nonce_str); // 商户订单号
			parm.put("openid", openid); // 用户openid
			parm.put("check_name", "NO_CHECK"); // 校验用户姓名选项 OPTION_CHECK
			// parm.put("re_user_name", "安迪");
			// //check_name设置为FORCE_CHECK或OPTION_CHECK，则必填
			parm.put("amount", amount); // 转账金额
			parm.put("desc", title); // 企业付款描述信息
			parm.put("spbill_create_ip", PayUtil.getLocalIp(request)); // Ip地址
			parm.put("sign", PayUtil.getSign(parm, "08dc51acad3502c6777c36962a8483a1"));
			String restxml = HttpUtils.posts("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers",
					XMLUtil.xmlFormat(parm, false));
			restmap = XMLUtil.xmlParse(restxml);
		} catch (Exception e) {
			// LOG.error(e.getMessage(), e);
		}

		if (CollectionUtil.isNotEmpty(restmap) && "SUCCESS".equals(restmap.get("result_code"))) {
			System.out.println("转账成功：" + restmap.get("err_code") + ":" + restmap.get("err_code_des"));
			Map<String, String> transferMap = new HashMap<>();
			transferMap.put("partner_trade_no", restmap.get("partner_trade_no"));// 商户转账订单号
			transferMap.put("payment_no", restmap.get("payment_no")); // 微信订单号
			transferMap.put("payment_time", restmap.get("payment_time")); // 微信支付成功时间
			WebUtil.response(response, WebUtil.packJsonp("", JSON.toJSONString(
					new JsonResult(1, "转账成功", new ResponseData(null, transferMap)), SerializerFeatureUtil.FEATURES)));
		} else {
			if (CollectionUtil.isNotEmpty(restmap)) {
				System.out.println("转账失败：" + restmap.get("err_code") + ":" + restmap.get("err_code_des"));
			}
			WebUtil.response(response, WebUtil.packJsonp("",
					JSON.toJSONString(new JsonResult(-1, "转账失败", new ResponseData()), SerializerFeatureUtil.FEATURES)));
		}

	}

	public static String getLocalIp(HttpServletRequest request) {
		String remoteAddr = request.getRemoteAddr();
		String forwarded = request.getHeader("X-Forwarded-For");
		String realIp = request.getHeader("X-Real-IP");

		String ip = null;
		if (realIp == null) {
			if (forwarded == null) {
				ip = remoteAddr;
			} else {
				ip = remoteAddr + "/" + forwarded.split(",")[0];
			}
		} else {
			if (realIp.equals(forwarded)) {
				ip = realIp;
			} else {
				if (forwarded != null) {
					forwarded = forwarded.split(",")[0];
				}
				ip = realIp + "/" + forwarded;
			}
		}
		return ip;
	}

	/**
	 * 查询拥有商品的商家
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("findShopMenuByName")
	@ResponseBody
	public void findShopMenuByName(ShopMenu shopMenu, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		List list = services.findShopMenuByName(shopMenu);
		// int yid =Integer.parseInt(request.getParameter("yid"));
		// Youhui youhui = new Youhui();
		// youhui.setId(yid);
		// youhui.setStates(1);
		// services.updateYhState(youhui);
		Gson gson = new Gson();
		String json = gson.toJson(list);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 修改订单状态
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("updateStates")
	@ResponseBody
	public void updateStates(Orders orders, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int a = services.updateStates(orders);
		// int yid =Integer.parseInt(request.getParameter("yid"));
		// Youhui youhui = new Youhui();
		// youhui.setId(yid);
		// youhui.setStates(1);
		// services.updateYhState(youhui);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 查询所有用户
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("findAllUser")
	@ResponseBody
	public void findAllUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		List list = services.findAllUser();
		Gson gson = new Gson();
		String json = gson.toJson(list);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	@ResponseBody
	@RequestMapping("getToken")
	public void getToken(HttpServletRequest request, HttpServletResponse responses) throws Exception {
		System.out.println("jkljlkjlk");
		String token = new GetToken().getToken();
		String ticket = new GetToken().getTicket(token);
		Map map = new HashMap<>();
		map.put("token", token);
		map.put("ticket", ticket);
		Gson gson = new Gson();
		String json = gson.toJson(map);
		PrintWriter pw = responses.getWriter();
		System.out.println(json);
		pw.write(json);
		pw.close();

	}

	@ResponseBody
	@RequestMapping("getTicket")
	public void getTicket(HttpServletRequest request, HttpServletResponse responses) throws Exception {
		System.out.println("jkljlkjlk");
		String jsapi_ticket = request.getParameter("ticket");
		String timestamp = System.currentTimeMillis() / 1000 + "";
		String appid = "wxd50232da55ddffbc";
		String noncestr = getRandomString(8);
		String url = request.getParameter("url");

		String sign = new SignUtils().getSign(jsapi_ticket, noncestr, timestamp, url);
		Map map = new HashMap<>();
		map.put("appid", appid);
		map.put("timestamp", timestamp);
		map.put("nonceStr", noncestr);
		map.put("signature", sign);

		Gson gson = new Gson();
		String json = gson.toJson(map);

		PrintWriter pw = responses.getWriter();
		System.out.println(json);
		pw.write(json);
		pw.close();

	}

	/**
	 * 生成随机字符串
	 * 
	 * @param length
	 *            字符串长度
	 * @return
	 */
	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	// 微信下单支付
	@ResponseBody
	@RequestMapping("doOrder")
	public void doOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, JDOMException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 得到openid
		String openid = request.getParameter("openid");
		System.out.println(request.getParameter("price"));
		int fee = 0;
		// 得到小程序传过来的价格，注意这里的价格必须为整数，1代表1分，所以传过来的值必须*100；
		if (null != request.getParameter("price")) {
			fee = Integer.parseInt(request.getParameter("price"));
		}
		String body = request.getParameter("body");
		System.out.println(body);
		// 订单编号
		String TT_id = request.getParameter("dingdanhao");
		// 订单标题
		// 时间戳
		String times = System.currentTimeMillis() + "";
		SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
		packageParams.put("appid", "wxd3270e7769759ce7");
		packageParams.put("mch_id", "1541631801");
		packageParams.put("nonce_str", times);// 时间戳
		packageParams.put("body", body);// 支付主体
		packageParams.put("out_trade_no", TT_id);// 商户订单号
		packageParams.put("total_fee", fee);// 价格
		packageParams.put("notify_url", url + "/myshop/notify");// 支付返回地址
		packageParams.put("trade_type", "JSAPI");// 这个api有，固定的
		packageParams.put("openid", openid);// openid
		// 获取sign
		String sign = PayCommonUtil.createSign("UTF-8", packageParams, "08dc51acad3502c6777c36962a8483a1");// 最后这个是自己设置的32位密钥
		packageParams.put("sign", sign);
		// 转成XML
		String requestXML = PayCommonUtil.getRequestXml(packageParams);
		System.out.println(requestXML);
		// 得到含有prepay_id的XML
		String resXml = HttpUtil.postData("https://api.mch.weixin.qq.com/pay/unifiedorder", requestXML);
		System.out.println(resXml);
		// 解析XML存入Map
		Map map = XMLUtil.doXMLParse(resXml);
		System.out.println(map);
		// String return_code = (String) map.get("return_code");
		// 得到prepay_id
		String prepay_id = (String) map.get("prepay_id");
		SortedMap<Object, Object> packageP = new TreeMap<Object, Object>();
		packageP.put("appId", "wxd3270e7769759ce7");// ！！！注意，这里是appId,上面是appid，真怀疑写这个东西的人。。。
		packageP.put("nonceStr", times);// 时间戳
		packageP.put("package", "prepay_id=" + prepay_id);// 必须把package写成
															// "prepay_id="+prepay_id这种形式
		packageP.put("signType", "MD5");// paySign加密
		packageP.put("timeStamp", "y" + times);
		// 得到paySign
		String paySign = PayCommonUtil.createSign("UTF-8", packageP, "08dc51acad3502c6777c36962a8483a1");
		packageP.put("paySign", paySign);
		// 将packageP数据返回给小程序
		Gson gson = new Gson();
		String json = gson.toJson(packageP);
		PrintWriter pw = response.getWriter();
		System.out.println(json);
		pw.write(json);
		pw.close();
	}

	/**
	 * 退款
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("refound")
	@ResponseBody
	public void refound(String out_trade_no, String total_fee, String refund_fee, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String out_refund_no = System.currentTimeMillis() + "";
		String nonce_str = System.currentTimeMillis() / 1000 + "";

		Map map = new WXPayRefundUtil().wechatRefund(out_refund_no, out_trade_no, total_fee, refund_fee, nonce_str);
		int a = 0;
		if ("OK".equals(map.get("return_msg"))) {
			Orders orders = new Orders();
			orders.setOrdernum(out_trade_no);
			orders.setState("5");
			a = services.updateState(orders);
		}
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 支付回调
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("notify")
	@ResponseBody
	public void notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resXml = "";
		Map<String, String> backxml = new HashMap<String, String>();
		
		InputStream inStream;
		try {
			inStream = request.getInputStream();
			ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				outSteam.write(buffer, 0, len);
			}
			System.out.println("微信支付----付款成功----");
			outSteam.close();
			inStream.close();
			String result = new String(outSteam.toByteArray(), "utf-8");// 获取微信调用我们notify_url的返回信息
			System.out.println("微信支付----result----=" + result);
			Map<Object, Object> map = XMLUtil.doXMLParse(result);

			DecimalFormat df = new DecimalFormat("#.00");
			if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
				System.out.println("微信支付----返回成功");
				// if (verifyWeixinNotify(map)) {
				// 订单处理 操作 orderconroller 的回写操作?
				System.out.println("微信支付----验证签名成功");
				// backxml.put("return_code", "<![CDATA[SUCCESS]]>");
				// backxml.put("return_msg", "<![CDATA[OK]]>");
				// // 告诉微信服务器，我收到信息了，不要在调用回调action了
				// strbackxml = pay.ArrayToXml(backxml);
				// response.getWriter().write(strbackxml);
				// logger.error("微信支付 ~~~~~~~~~~~~~~~~执行完毕？backxml=" +
				// strbackxml);

				// ====================================================================
				// 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
				resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
						+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

				// 处理业务 -修改订单支付状态
				System.out.println("微信支付回调：修改的订单=" + map.get("out_trade_no"));
				String status = services.findOrderStatus(map.get("out_trade_no").toString());
				Orders orders = new Orders();
				orders.setOrdernum(map.get("out_trade_no").toString());
				orders.setState("1");
				int a = services.updateState(orders);
				// int editres = Wechat_Order.execute("UPDATE wechat_order SET paystatus =?
				// WHERE orderno=?",
				// new Object[] { EnumPayStatus.Paybackok.getValue(), map.get("out_trade_no")
				// });
				if (a > 0) {
					System.out.println("微信支付回调：修改订单支付状态成功");
				} else {
					System.out.println("微信支付回调：修改订单支付状态失败");
				}
				// 修改库存销量变化
				List<Orders> list = services.findOrderByNum(map.get("out_trade_no").toString());
				String phone[] = new String[list.size()];
				
				boolean flag = false;
				for (int i = 0; i < list.size(); i++) {
					Shops shops = services.findShopByNum(list.get(i).getSid());
					phone[i] = shops.getPhone();
					if (i == list.size() - 1) {
						flag = true;
					}
				}
				if (flag) {
					String[] area = { "" };
					sendSms(phone, 371691, area);
				}
				for (int i = 0; i < list.size(); i++) {
					int b = 0;
					int c = 0;
					int d = 0;
					int e = 0;
					while (b == 0) {
						JSONObject jsonObject = JSONObject.fromObject(list.get(i).getContent());
						ShopMenu shopMenu = new ShopMenu();
						shopMenu.setColors(jsonObject.getString("colors"));
						shopMenu.setMid(jsonObject.getInt("mid"));
						shopMenu.setSize(jsonObject.getString("size"));
						shopMenu.setNumber(jsonObject.getString("sid"));
						ShopMenu sMenu = services.findShopMenuByMid(shopMenu);
						Map map1 = new HashMap<>();
						map1.put("id", sMenu.getId());
						int s = 0;
						if (Integer.parseInt(sMenu.getKucun()) - jsonObject.getInt("num") <= 0) {
							s = 0;
						} else {
							s = Integer.parseInt(sMenu.getKucun()) - jsonObject.getInt("num");
						}
						map1.put("kucun", s);
						map1.put("num", sMenu.getNum() + jsonObject.getInt("num"));
						map1.put("kucuns", Integer.parseInt(sMenu.getKucun()));
						map1.put("nums", sMenu.getNum());
						b = services.updateShopMenuKucun(map1);
					}
					while (c == 0) {
						JSONObject jsonObject = JSONObject.fromObject(list.get(i).getContent());
						ShopMenu shopMenu = new ShopMenu();
						int mid = jsonObject.getInt("mid");
						Menus menu = services.findMenuById(mid);
						Map map1 = new HashMap<>();
						map1.put("id", menu.getId());
						map1.put("num", menu.getNum() + jsonObject.getInt("num"));
						map1.put("nums", menu.getNum());
						c = services.updateMenuNum(map1);
						// b = services.updateShopMenuKucun(sMenu);
					}
					JSONObject jsonObject = JSONObject.fromObject(list.get(i).getContent());
					int mid = jsonObject.getInt("mid");
					Menus menu = services.findMenuById(mid);
					Fenxiao fenxiao = new Fenxiao();
					fenxiao = services.findFenxiaoById(Integer.parseInt(menu.getGhid()));
					if(fenxiao.getGrade()!=0) {
					while (d == 0) {
						Users users1 = services.findUserByOpenid(list.get(i).getOpenid());
						Users users2 = services.findUserByTjm(users1.getToptjm());
						if (users2 != null) {
							double money = Double.parseDouble(users2.getMoney())
									+ (Double.parseDouble(list.get(i).getPrice()) * fenxiao.getOne());
							Map map1 = new HashMap<>();
							map1.put("mytjm", users2.getMytjm());
							if (Integer.parseInt(users2.getGrade()) == 1) {
								if (money >= 200) {
									map1.put("money", df.format(200));
								} else {
									map1.put("money", df.format(money));
								}
							}
							map1.put("moneys", users2.getMoney());
							d = services.updateMoney(map1);
						} else {
							d = 1;
						}

					}
					if (fenxiao.getGrade() == 2) {
						while (e == 0) {
							Users users1 = services.findUserByOpenid(list.get(i).getOpenid());
							Users users2 = services.findUserByTjm(users1.getToptjm());
							Users users3 = services.findUserByTjm(users2.getToptjm());
							if (users3 != null) {
								double money = Double.parseDouble(users3.getMoney())
										+ (Double.parseDouble(list.get(i).getPrice()) * fenxiao.getTwo());
								Map map1 = new HashMap<>();
								map1.put("mytjm", users3.getMytjm());
								if (Integer.parseInt(users3.getGrade()) == 1) {
									if (money >= 200) {
										map1.put("money", df.format(200));
									} else {
										map1.put("money", df.format(money));
									}
								}

								map1.put("moneys", users3.getMoney());
								e = services.updateMoney(map1);
							} else {
								e = 1;
							}

						}
						
					}
					}
					System.out.println("付款回执：---------" + a + b + c + d + e);
				}

				// }
				// ------------------------------
				// 处理业务完毕
				// ------------------------------
				System.out.println("支付----------------------------------");
				BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
				out.write(resXml.getBytes());
				out.flush();
				out.close();
			}
			// else {
			// logger.info("支付失败,错误信息：" + map.get("err_code"));
			// resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
			// + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
			// }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}

		// return resXml;

	}

	public boolean verifyWeixinNotify(Map<Object, Object> map) {
		SortedMap<Object, Object> parameterMap = new TreeMap<Object, Object>();
		String sign = (String) map.get("sign");
		for (Object keyValue : map.keySet()) {
			if (!keyValue.toString().equals("sign")) {
				parameterMap.put(keyValue.toString(), map.get(keyValue).toString());
			}
		}
		String createSign = PayCommonUtil.getRequestXml(parameterMap);
		if (createSign.equals(sign)) {
			return true;
		} else {
			System.out.println("微信支付  ~~~~~~~~~~~~~~~~验证签名失败");
			return false;
		}

	}

	@RequestMapping("notifys")
	@ResponseBody
	public void notifys(String order, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		System.out.println("-----------------------------------------------");
		System.out.println("成功会掉");
		// System.out.println(ordernum);
		System.out.println("------------------------------------------------");
		String status = services.findOrderStatus(order);
		if ("0".equals(status) || Integer.parseInt(status) == 0) {
			Orders orders = new Orders();
			orders.setOrdernum(order);
			orders.setState("1");
			int a = services.updateState(orders);
			System.out.println(status);
		}

		/*
		 * int a = services.updateHyType(user); Gson gson = new Gson(); String json =
		 * gson.toJson(a); PrintWriter pw = response.getWriter(); pw.write(json);
		 * pw.close();
		 */
	}

	/**
	 * 预约状态修改
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("updateHyType")
	@ResponseBody
	public void updateHyType(Users user, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int a = services.updateHyType(user);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 根据id查询订单
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("findMenuById")
	@ResponseBody
	public void findMenuById(int mid, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		Menus a = services.findMenuById(mid);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 生成二维码
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("addScan")
	@ResponseBody
	public void addScan(String path, String one, String two, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String token = new GetToken().getToken();
		System.out.println(path);
		System.out.println(one);
		System.out.println(two);
		String url = new Scans().getminiqrQr(path, one, two, token);
		Gson gson = new Gson();
		String json = gson.toJson(url);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 管理员查询
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("findByPhone")
	@ResponseBody
	public void findByPhone(Admin admin, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		Admin ad = services.findByPhone(admin);
		String json;
		Gson gson = new Gson();

		if (ad == null) {
			json = gson.toJson(0);
		} else {
			json = gson.toJson(ad);
		}
		System.out.println(ad);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 店铺登录
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("findShopsByPhoneAndNumber")
	@ResponseBody
	public void findShopsByPhoneAndNumber(Shops admin, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		Shops ad = services.findShopsByPhoneAndNumber(admin);
		String json;
		Gson gson = new Gson();

		if (ad == null) {
			json = gson.toJson(0);
		} else {
			json = gson.toJson(ad);
		}

		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 查询所有管理员
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("findAllAmin")
	@ResponseBody
	public void findAllAmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		List list = services.findAllAmin();
		Gson gson = new Gson();
		String json = gson.toJson(list);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 添加管理员
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("insertAdmin")
	@ResponseBody
	public void insertAdmin(Admin admin, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		Admin ad = services.findAminByPhone(admin.getPhone());
		int a = 0;
		if (ad != null) {
			a = 2;
		} else {
			a = services.insertAdmin(admin);
		}
		response.sendRedirect("/supermarket/addAdmin.jsp");
	}

	/**
	 * 修改管理员密码
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("updateAdminPwd")
	@ResponseBody
	public void updateAdminPwd(Admin admin, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int a = services.updateAdminPwd(admin);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 图片上传
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("uploadImg")
	@ResponseBody
	public void uploadImg(MultipartFile file, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String img = url + new FileUploadUtils().uploadImgFile(request, file);
		System.out.println(img);
		Gson gson = new Gson();
		String json = gson.toJson(img);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 图片上传
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("uploadImgs")
	@ResponseBody
	public void uploadImgs(MultipartFile file, int id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String img = url + new FileUploadUtils().uploadImgFile(request, file);
		Menus menus = new Menus();
		menus.setId(id);
		menus.setImg(img.replaceAll("\"", ""));
		int a = services.updateMenuImg(menus);
		System.out.println(img.replaceAll("\"", ""));
		Gson gson = new Gson();
		String json = gson.toJson(img);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	// 分页查询訂單
	@ResponseBody
	@RequestMapping("fenyeOrder")
	public void fenyeOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		List list = new ArrayList<>();
		int id = new Integer(request.getParameter("id"));
		String name = "%" + request.getParameter("name") + "%";
		String sid = "%" + request.getParameter("sid") + "%";
		Map map = new HashMap();
		map.put("start", (id - 1) * 10);
		map.put("end", 10);
		map.put("name", name);
		map.put("sid", sid);
		list = services.fenyeOrder(map);
		System.out.println(list);
		Gson gson = new Gson();
		String json = gson.toJson(list);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	// 訂單数量
	@ResponseBody
	@RequestMapping("countOrder")
	public void countOrder(String name, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		int a = services.countOrder("%" + name + "%");
		System.out.println(a);
		// 锟街讹拷锟斤拷页
		int page = 0;
		if (a % 10 == 0) {
			page = a / 10;
		} else {
			page = a / 10 + 1;
		}
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("count", a);
		map.put("page", page);
		Gson gson = new Gson();
		String json = gson.toJson(map);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	// 分页查询用户
	@ResponseBody
	@RequestMapping("fenyeUser")
	public void fenyeUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		List list = new ArrayList<>();
		int id = new Integer(request.getParameter("id"));
		Map map = new HashMap();
		map.put("start", (id - 1) * 10);
		map.put("end", 10);
		list = services.fenyeUser(map);
		System.out.println(list);
		Gson gson = new Gson();
		String json = gson.toJson(list);
		PrintWriter pw = response.getWriter();
		pw.write(URLDecoder.decode(json, "utf-8"));
		pw.close();
	}

	// 用户数量
	@ResponseBody
	@RequestMapping("countUser")
	public void countUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		int a = services.countUser();
		System.out.println(a);
		// 锟街讹拷锟斤拷页
		int page = 0;
		if (a % 10 == 0) {
			page = a / 10;
		} else {
			page = a / 10 + 1;
		}
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("count", a);
		map.put("page", page);
		Gson gson = new Gson();
		String json = gson.toJson(map);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	// 分页查询店铺
	@ResponseBody
	@RequestMapping("fenyeShop")
	public void fenyeShop(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		List list = new ArrayList<>();
		int id = new Integer(request.getParameter("id"));
		int ghid = new Integer(request.getParameter("ghid"));
		String status = request.getParameter("status");
		String ghids="";
		if(ghid==0) {
			
		}else{
			ghids="and ghid="+ghid;
		}
		Map map = new HashMap();
		map.put("start", (id - 1) * 10);
		map.put("end", 10);
		map.put("status", "%" + status + "%");
		map.put("ghid", ghids);
		list = services.fenyeShop(map);
		System.out.println(list);
		Gson gson = new Gson();
		String json = gson.toJson(list);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	// 用户数量
	@ResponseBody
	@RequestMapping("countShop")
	public void countShop(int ghid,HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String status = request.getParameter("status");
		String ghids="";
		if(ghid==0) {
			
		}else{
			ghids="and ghid="+ghid;
		}
		Map maps = new HashMap();
		maps.put("status", "%" + status + "%");
		maps.put("ghid", ghids);
		int a = services.countShop(maps);
		System.out.println(a);
		// 锟街讹拷锟斤拷页
		int page = 0;
		if (a % 10 == 0) {
			page = a / 10;
		} else {
			page = a / 10 + 1;
		}
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("count", a);
		map.put("page", page);
		Gson gson = new Gson();
		String json = gson.toJson(map);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	// 根據id查詢訂單
	@ResponseBody
	@RequestMapping("findOrderById")
	public void findOrderById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		List list = new ArrayList<>();
		int id = new Integer(request.getParameter("id"));
		// Map map = new HashMap();
		// map.put("start", (id - 1) * 10);
		// map.put("end", 10);
		Orders orders = services.findOrderById(id);
		System.out.println(orders);
		Gson gson = new Gson();
		String json = gson.toJson(orders);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	// 添加菜单
	@ResponseBody
	@RequestMapping("insertMenu")
	public void insertMenu(Menus menus, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		System.out.println(menus.getImg());
		System.out.println(menus.getImgs());
		int id = services.insertMenu(menus);
		// Map map = new HashMap();
		// map.put("start", (id - 1) * 10);
		// map.put("end", 10);
		Gson gson = new Gson();
		String json = gson.toJson(id);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	// 审核店铺
	@ResponseBody
	@RequestMapping("updateShopState")
	public void updateShopState(Shops shops, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		int id = services.updateShopState(shops);
		String[] phone = { shops.getPhone() };
		String[] area = { "" };
		sendSms(phone, 373692, area);
		// Map map = new HashMap();
		// map.put("start", (id - 1) * 10);
		// map.put("end", 10);
		Gson gson = new Gson();
		String json = gson.toJson(id);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	// 审核店铺
	@ResponseBody
	@RequestMapping("updateToptjm")
	public void updateToptjm(Users user, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		int id = services.updateToptjm(user);
		// Map map = new HashMap();
		// map.put("start", (id - 1) * 10);
		// map.put("end", 10);
		Gson gson = new Gson();
		String json = gson.toJson(id);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	// 分页查询菜单
	@ResponseBody
	@RequestMapping("fenyeMenu")
	public void fenyeMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		List list = new ArrayList<>();
		int id = new Integer(request.getParameter("id"));
		String name = "%" + request.getParameter("name") + "%";
		String types = "%" + request.getParameter("status") + "%";
		String ghid = request.getParameter("ghid");
		Map map = new HashMap();
		map.put("start", (id - 1) * 10);
		map.put("end", 10);
		map.put("name", name);
		map.put("types", types);
		map.put("ghid", ghid);
		list = services.fenyeMenu(map);
		System.out.println(list);
		Gson gson = new Gson();
		String json = gson.toJson(list);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	// 菜单数量
	@ResponseBody
	@RequestMapping("countMenu")
	public void countMenu(String name, String status, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String ghid = request.getParameter("ghid");
		Map map = new HashMap<>();
		map.put("name", "%" + name + "%");
		map.put("types", "%" + status + "%");
		map.put("ghid", ghid);
		System.out.println("----------------");
		System.out.println(map);
		int a = services.countMenu(map);
		System.out.println("数量" + a);
		// 锟街讹拷锟斤拷页
		int page = 0;
		if (a % 10 == 0) {
			page = a / 10;
		} else {
			page = a / 10 + 1;
		}
		Map<String, Integer> maps = new HashMap<String, Integer>();
		maps.put("count", a);
		maps.put("page", page);
		Gson gson = new Gson();
		String json = gson.toJson(maps);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	// 删除菜单
	@ResponseBody
	@RequestMapping("delMenu")
	public void delMenu(int id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		int a = services.delMenu(id);
		// Map map = new HashMap();
		// map.put("start", (id - 1) * 10);
		// map.put("end", 10);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	// 分页查询菜单
	@ResponseBody
	@RequestMapping("fenyeAdmin")
	public void fenyeAdmin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		List list = new ArrayList<>();
		int id = new Integer(request.getParameter("id"));
		Map map = new HashMap();
		map.put("start", (id - 1) * 10);
		map.put("end", 10);
		list = services.fenyeAdmin(map);
		System.out.println(list);
		Gson gson = new Gson();
		String json = gson.toJson(list);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	// 菜单数量
	@ResponseBody
	@RequestMapping("countAdmin")
	public void countAdmin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		int a = services.countAdmin();
		System.out.println(a);
		// 锟街讹拷锟斤拷页
		int page = 0;
		if (a % 10 == 0) {
			page = a / 10;
		} else {
			page = a / 10 + 1;
		}
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("count", a);
		map.put("page", page);
		Gson gson = new Gson();
		String json = gson.toJson(map);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	// 删除菜单
	@ResponseBody
	@RequestMapping("delAdmin")
	public void delAdmin(int id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		int a = services.delAdmin(id);
		// Map map = new HashMap();
		// map.put("start", (id - 1) * 10);
		// map.put("end", 10);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	// 查詢類別
	@ResponseBody
	@RequestMapping("findTypes")
	public void findTypes(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		List list = services.findTypes();
		// Map map = new HashMap();
		// map.put("start", (id - 1) * 10);
		// map.put("end", 10);
		Gson gson = new Gson();
		String json = gson.toJson(list);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	// 查詢類別
	@ResponseBody
	@RequestMapping("updateMeneStatus")
	public void updateMeneStatus(Menus menus, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		int t = services.updateMeneStatus(menus);
		// Map map = new HashMap();
		// map.put("start", (id - 1) * 10);
		// map.put("end", 10);
		Gson gson = new Gson();
		String json = gson.toJson(t);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	// 批量上架、下架
	@ResponseBody
	@RequestMapping("updateMeneAllStatus")
	public void updateMeneAllStatus(String a, int status, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		System.out.println(a);
		JSONArray jsons = JSONArray.fromObject(a);
		int count = 0;
		if (jsons.size() > 0) {
			Menus menus = new Menus();
			for (int i = 0; i < jsons.size(); i++) {
				menus.setId(Integer.parseInt(jsons.get(i).toString()));
				menus.setStatus(status);
				services.updateMeneStatus(menus);
				count++;
				System.out.println(jsons.get(i)); // 得到 每个对象中的属性值
			}
		}
		// int t = services.updateMeneStatus(menus);
		// Map map = new HashMap();
		// map.put("start", (id - 1) * 10);
		// map.put("end", 10);
		Gson gson = new Gson();
		String json = gson.toJson(count);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	// 批量修改分类
	@ResponseBody
	@RequestMapping("updateAllMenuType")
	public void updateAllMenuType(String a, String types, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		System.out.println(a);
		System.out.println(types);
		JSONArray jsons = JSONArray.fromObject(a);
		int count = 0;
		if (jsons.size() > 0) {
			Menus menus = new Menus();
			for (int i = 0; i < jsons.size(); i++) {
				menus.setId(Integer.parseInt(jsons.get(i).toString()));
				menus.setTypes(types);

				services.updateAllMenuType(menus);
				count++;
				System.out.println(jsons.get(i)); // 得到 每个对象中的属性值
			}
		}
		// int t = services.updateMeneStatus(menus);
		// Map map = new HashMap();
		// map.put("start", (id - 1) * 10);
		// map.put("end", 10);
		Gson gson = new Gson();
		String json = gson.toJson(count);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	// 更改全部分类
	@ResponseBody
	@RequestMapping("updateMenuType")
	public void updateMenuType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Map map = new HashMap<>();
		map.put("one", request.getParameter("one"));
		map.put("two", request.getParameter("two"));
		int a = services.updateMenuType(map);
		// Map map = new HashMap();
		// map.put("start", (id - 1) * 10);
		// map.put("end", 10);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 修改商品信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("updateMenu")
	@ResponseBody
	public void updateMenu(Menus menus, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int a = services.updateMenu(menus);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 添加优惠券
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("insertYh")
	@ResponseBody
	public void insertYh(Youhui youhui, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String bigdate = request.getParameter("bigdate");
		System.out.println(youhui);
		// youhui.setBigdate(bigdate);
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String d = simpleDateFormat.format(date);
		youhui.setDates(d);
		int a = services.insertYh(youhui);
		Users users = new Users();
		users.setOpenid(youhui.getOpenid());
		users.setNews(1);
		int b = services.updateNews(users);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 删除优惠券
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("delYh")
	@ResponseBody
	public void delYh(int id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int a = services.delYh(id);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 根据openid查询优惠券
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("findYhList")
	@ResponseBody
	public void findYhList(String openid, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		List list = services.findYhList(openid);
		Gson gson = new Gson();
		String json = gson.toJson(list);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 删除优惠券
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("updateYhState")
	@ResponseBody
	public void updateYhState(Youhui youhui, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int a = services.updateYhState(youhui);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 优惠券过期检测
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("youhuiGq")
	@ResponseBody
	public void youhuiGq(String openid, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		List<Youhui> list = services.findYhList(openid);
		System.out.println(list);

		Youhui youhui = new Youhui();
		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			if (datesbm(list.get(i).getDates()) > Integer.parseInt(list.get(i).getBigdate())
					&& list.get(i).getStates() == 0) {
				youhui.setId(list.get(i).getId());
				youhui.setStates(2);
				System.out.println(youhui);
				services.updateYhState(youhui);
				count++;
			}
		}
		System.out.println(count);
		Gson gson = new Gson();
		String json = gson.toJson(count);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	// 判断时间过期
	public int datesbm(String yhdate) throws Exception {
		String format = "yyyy-MM-dd";
		Date YhTime = new SimpleDateFormat(format).parse(yhdate);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		String date = df.format(new Date());
		Date NowTime = new SimpleDateFormat(format).parse(date);
		int a = 0;
		a = (int) ((NowTime.getTime() - YhTime.getTime()) / (1000 * 60 * 60 * 24));
		System.out.println("时间差=" + a);
		return a;
	}

	/**
	 * 删除优惠券
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("findMenuByName")
	@ResponseBody
	public void findMenuByName(String name, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		List list = services.findMenuByName("%" + name + "%");
		Gson gson = new Gson();
		String json = gson.toJson(list);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 判断并插入助力
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("insertHelps")
	@ResponseBody
	public void insertHelps(Helps helps, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int a = services.countHelps(helps);
		int b = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dates = format.format(new Date());
		if (a == 0) {

			b = services.insertHelps(helps);
			int c = services.countMyHelps(helps);
			if (c == 5) {
				Youhui youhui = new Youhui();
				youhui.setOpenid(helps.getMyopenid());
				youhui.setStates(0);
				youhui.setLow("50");
				youhui.setTips("好友助力获得");
				youhui.setTypes("满减");
				youhui.setBigdate("15");
				youhui.setUp("5");
				youhui.setDates(dates);
				services.insertYh(youhui);
				Users users = new Users();
				users.setOpenid(helps.getMyopenid());
				users.setNews(2);
				services.updateNews(users);
			}
		}
		Gson gson = new Gson();
		String json = gson.toJson(b);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 判断并插入助力
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("countMyHelps")
	@ResponseBody
	public void countMyHelps(Helps helps, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int a = services.countMyHelps(helps);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 插入店铺
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("insertShops")
	@ResponseBody
	public void insertShops(Shops shops, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		Shops shops2 = services.findShopById(shops.getOpenid());
		String number = "YQ" + System.currentTimeMillis() / 100;
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String d = format.format(date);
		shops.setNumber(number);
		shops.setDates(d);
		System.out.println(shops2);
		int a = 0;
		if (shops2 != null) {
			a = 0;
		} else {

			a = services.insertShops(shops);
		}
		Map map = new HashMap<>();
		map.put("status", a);
		map.put("number", number);
		Gson gson = new Gson();
		String json = gson.toJson(map);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 根据id查询店铺
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("findShopById")
	@ResponseBody
	public void findShopById(String openid, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		Shops shops2 = services.findShopById(openid);
		Gson gson = new Gson();
		String json = gson.toJson(shops2);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 根据编号查询店铺
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("findShopByNum")
	@ResponseBody
	public void findShopByNum(String number, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		Shops shops2 = services.findShopByNum(number);
		Gson gson = new Gson();
		String json = gson.toJson(shops2);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 查询店铺
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("findShop")
	@ResponseBody
	public void findShop(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		List list = services.findShop();
		Gson gson = new Gson();
		String json = gson.toJson(list);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 删除店铺
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("delShops")
	@ResponseBody
	public void delShops(int id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int a = services.delShops(id);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 插入店铺
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("insertSwiper")
	@ResponseBody
	public void insertSwiper(MultipartFile file, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String img = url + new FileUploadUtils().uploadImgFile(request, file);
		int a = services.insertSwiper(img);
		response.sendRedirect("/supermarket/addSwiper.jsp");
	}

	/**
	 * 查询店铺
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("findSwiper")
	@ResponseBody
	public void findSwiper(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		List list = services.findSwiper();
		Gson gson = new Gson();
		String json = gson.toJson(list);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 删除店铺
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("delSwiper")
	@ResponseBody
	public void delSwiper(int id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int a = services.delSwiper(id);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 添加地址
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("insertAddr")
	@ResponseBody
	public void insertAddr(com.xbboomOrder.bean.Address addr, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String d = simpleDateFormat.format(date);
		addr.setDates(d);
		int a = services.insertAddr(addr);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 删除地址
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("delAddr")
	@ResponseBody
	public void delAddr(int id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int a = services.delAddr(id);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 更改地址
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("updateAddr")
	@ResponseBody
	public void updateAddr(com.xbboomOrder.bean.Address address, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int a = services.updateAddr(address);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 更改地址
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("updateShops")
	@ResponseBody
	public void updateShops(Orders orders, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int a = services.updateShops(orders);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 查询地址
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("findAddr")
	@ResponseBody
	public void findAddr(String openid, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		List list = services.findAddr(openid);
		Gson gson = new Gson();
		String json = gson.toJson(list);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 查询地址
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("findAddrByIs")
	@ResponseBody
	public void findAddrByIs(Address address, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		Address address2 = services.findAddrByIs(address);
		Gson gson = new Gson();
		String json = gson.toJson(address2);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 设置默认地址
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("changeAddrIs")
	@ResponseBody
	public void changeAddrIs(Address address, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int a = 0;
		int b = services.noAddrIs(address.getOpenid());
		if (b > 0) {
			a = services.changeAddrIs(address.getId());
		}

		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 添加购物车
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("insertCar")
	@ResponseBody
	public void insertCar(Mycar mycar, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		System.out.println(mycar.getMid());
		System.out.println(mycar.getOpenid());
		Mycar car = services.jianceCar(mycar);
		System.out.println(car);
		int a = 0;
		if (car == null) {
			System.out.println("不存在");
			int b = services.insertCar(mycar);
			if (b > 0) {
				a = 1;
			}
		} else {
			System.out.println("存在");
			car.setNum(car.getNum() + mycar.getNum());
			int c = services.updateCarNum(car);
			if (c > 0) {
				a = 2;
			}
		}
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 删除购物车
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("delCar")
	@ResponseBody
	public void delCar(String ids, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		System.out.println(ids);
		String[] string = ids.replace("[", "").replace("]", "").split(",");
		System.out.println(string);
		int a = 0;
		for (int i = 0; i < string.length; i++) {
			int b = services.delCar(Integer.parseInt(string[i]));
			if (b > 0) {
				a++;
			}
		}

		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 查询购物车
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("findCar")
	@ResponseBody
	public void findCar(String openid, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		List list = services.findCar(openid);
		Gson gson = new Gson();
		String json = gson.toJson(list);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 查询购物车
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("findOrdersByPphone")
	@ResponseBody
	public void findOrdersByPphone(String pphone, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		List list = services.findOrdersByPphone(pphone);
		Gson gson = new Gson();
		String json = gson.toJson(list);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 修改数量
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("updateCarNum")
	@ResponseBody
	public void updateCarNum(Mycar mycar, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int types = Integer.parseInt(request.getParameter("types"));
		Mycar car = services.findMyCarById(mycar.getId());
		if (types == 0) {
			if (car.getNum() > 1) {

				car.setNum(car.getNum() - 1);
			}
		} else {
			car.setNum(car.getNum() + 1);
		}
		int a = services.updateCarNum(car);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 修改数量
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("updatePeiSong")
	@ResponseBody
	public void updatePeiSong(Orders orders, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		Orders orders2 = services.findOrderById(orders.getId());
		int a = 0;
		if ("1".equals(orders2.getState())) {
			orders2.setState("2");
			services.updateState(orders2);
			a = services.updatePeiSong(orders);
		} else {
			a = 2;
		}
		// int a = services.updateCarNum(car);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 修改分类
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("updateTypes")
	@ResponseBody
	public void updateTypes(Types types, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		Types types2 = new Types();
		types2 = services.findTypeById(types.getId());
		Map map = new HashMap<>();
		map.put("one", types.getOne());
		map.put("two", types2.getOne());
		System.out.println(map);
		System.out.println(types2);
		int a = services.updateTypes(types);
		if (a > 0) {
			services.updateMenuType(map);
		}
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 修改公告
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("updateGg")
	@ResponseBody
	public void updateGg(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		Map map = new HashMap<>();
		map.put("title", request.getParameter("title"));
		int a = services.updateGg(map);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 查询公告
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("findGg")
	@ResponseBody
	public void findGg(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		Map map = services.findGg();
		Gson gson = new Gson();
		String json = gson.toJson(map);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 添加分类
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("insertTypes")
	@ResponseBody
	public void insertTypes(Types types, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		System.out.println(types.getJj());
		List<Types> list = services.findTypes();
		System.out.println(list.size());
		types.setTwo(list.get(list.size() - 1).getTwo() + 1);
		int a = services.insertTypes(types);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 分类排序
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("updateSxup")
	@ResponseBody
	public void updateSxup(int num, int id1, int id2, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		Map map = new HashMap<>();
		map.put("two", num - 1);
		map.put("id", id1);
		int a = services.updateSx(map);
		Map maps = new HashMap<>();
		maps.put("two", num);
		maps.put("id", id2);
		int b = services.updateSx(maps);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 分类排序
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("updateSxdown")
	@ResponseBody
	public void updateSxdown(int num, int id1, int id2, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		Map map = new HashMap<>();
		map.put("two", num + 1);
		map.put("id", id1);
		int a = services.updateSx(map);
		Map maps = new HashMap<>();
		maps.put("two", num);
		maps.put("id", id2);
		int b = services.updateSx(maps);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 修改分类图片
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("updateTypesImg")
	@ResponseBody
	public void updateTypesImg(Types types, MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String img = url + new FileUploadUtils().uploadImgFile(request, file);
		types.setImg(img);
		int a = services.updateTypesImg(types);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 修改店铺认证信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("updateShopsByNum")
	@ResponseBody
	public void updateShopsByNum(Shops shops, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int a = services.updateShopsByNum(shops);
		System.out.println(shops.getBank());
		System.out.println(shops.getZfb());
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 删除分类
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("delTypes")
	@ResponseBody
	public void delTypes(int id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int a = services.delTypes(id);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 删除店铺分类
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("delShopType")
	@ResponseBody
	public void delShopType(int id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int a = services.delShopType(id);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 查询店铺分类
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("findShopType")
	@ResponseBody
	public void findShopType(String number, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		List<Shoptype> list = services.findShopType(number);
		Gson gson = new Gson();
		String json = gson.toJson(list);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 修改店铺分类
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("updateShoptype")
	@ResponseBody
	public void updateShoptype(Shoptype shoptype, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int a = services.updateShoptype(shoptype);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 添加店铺分类
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("insertShoptype")
	@ResponseBody
	public void insertShoptype(Shoptype shoptype, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		List<Shoptype> list = services.findShopType(shoptype.getNumber());
		int px = 0;
		if (list.size() > 0) {
			System.out.println(list.get(list.size() - 1).getPx());
			px = list.get(list.size() - 1).getPx() + 1;
		} else {
			px = 1;
		}
		shoptype.setPx(px);
		int a = services.insertShoptype(shoptype);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 删除店铺商品
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("delShopMenu")
	@ResponseBody
	public void delShopMenu(int id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int a = services.delShopMenu(id);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	// 分页查询店铺商品
	@ResponseBody
	@RequestMapping("findShopMenu")
	public void findShopMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		List list = new ArrayList<>();
		int id = new Integer(request.getParameter("id"));
		String kucun = request.getParameter("kucun") + "%";
		String status = "%" + request.getParameter("status") + "%";
		String types = "%" + request.getParameter("types") + "%";
		String number = request.getParameter("number");
		Map map = new HashMap();
		map.put("start", (id - 1) * 10);
		map.put("end", 10);
		map.put("status", status);
		map.put("kucun", kucun);
		map.put("types", types);
		map.put("number", number);
		int a = services.countShopMenu(map);
		list = services.findShopMenu(map);

		int page = 0;
		if (a % 10 == 0) {
			page = a / 10;
		} else {
			page = a / 10 + 1;
		}
		Map maps = new HashMap<>();
		maps.put("count", a);
		maps.put("page", page);
		maps.put("list", list);
		System.out.println(maps);
		Gson gson = new Gson();
		String json = gson.toJson(maps);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	// 店铺商品数量
	@ResponseBody
	@RequestMapping("countShopMenu")
	public void countShopMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String kucun = "%" + request.getParameter("kucun") + "%";
		String status = "%" + request.getParameter("status") + "%";
		String types = "%" + request.getParameter("types") + "%";
		Map map = new HashMap();
		map.put("status", status);
		map.put("kucun", kucun);
		map.put("types", types);
		int a = services.countShopMenu(map);
		System.out.println(a);
		// 锟街讹拷锟斤拷页
		int page = 0;
		if (a % 10 == 0) {
			page = a / 10;
		} else {
			page = a / 10 + 1;
		}
		Map<String, Integer> maps = new HashMap<String, Integer>();
		maps.put("count", a);
		maps.put("page", page);
		Gson gson = new Gson();
		String json = gson.toJson(maps);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 上架下架
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("updateShopMenuStatus")
	@ResponseBody
	public void updateShopMenuStatus(ShopMenu shopMenu, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int a = services.updateShopMenuStatus(shopMenu);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 编辑商品信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("editShopMenu")
	@ResponseBody
	public void editShopMenu(ShopMenu shopMenu, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int a = services.editShopMenu(shopMenu);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/**
	 * 查询团队
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("findTuanDui")
	@ResponseBody
	public void findTuanDui(String toptjm, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		System.out.println(toptjm);
		List list = services.findTuanDui(toptjm);
		System.out.println(list);
		Gson gson = new Gson();
		String json = gson.toJson(list);
		PrintWriter pw = response.getWriter();
		pw.write(URLDecoder.decode(json, "utf-8"));
		pw.close();
	}

	/**
	 * 添加店铺商品
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("insertShopMenu")
	@ResponseBody
	public void insertShopMenu(ShopMenu shopMenu, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		ShopMenu list = services.findShopMenuByMid(shopMenu);
		int a = 0;
		if (list == null) {
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			shopMenu.setDates(format.format(date));
			a = services.insertShopMenu(shopMenu);
		} else {

		}

		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}
	// 查询所有供货商
		@ResponseBody
		@RequestMapping("findAllGhShop")
		public void findAllGhShop(HttpServletRequest request, HttpServletResponse response) throws Exception {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			List list = services.findAllGhShop();
			System.out.println(list);
			Gson gson = new Gson();
			String json = gson.toJson(list);
			PrintWriter pw = response.getWriter();
			pw.write(URLDecoder.decode(json, "utf-8"));
			pw.close();
		}
	// 分页查询供货商
	@ResponseBody
	@RequestMapping("fenyeGhShop")
	public void fenyeGhShop(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		List list = new ArrayList<>();
		int id = new Integer(request.getParameter("id"));
		String name = "%" + request.getParameter("name") + "%";
		Map map = new HashMap();
		map.put("start", (id - 1) * 10);
		map.put("end", 10);
		map.put("name", name);
		list = services.fenyeGhShop(map);
		System.out.println(list);
		Gson gson = new Gson();
		String json = gson.toJson(list);
		PrintWriter pw = response.getWriter();
		pw.write(URLDecoder.decode(json, "utf-8"));
		pw.close();
	}

	// 供货商数量
	@ResponseBody
	@RequestMapping("countGhshop")
	public void countGhshop(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String name = "%" + request.getParameter("name") + "%";
		Map maps = new HashMap();
		maps.put("name", name);
		int a = services.countGhshop(maps);
		System.out.println(a);
		// 锟街讹拷锟斤拷页
		int page = 0;
		if (a % 10 == 0) {
			page = a / 10;
		} else {
			page = a / 10 + 1;
		}
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("count", a);
		map.put("page", page);
		Gson gson = new Gson();
		String json = gson.toJson(map);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	// 添加供货商
	@ResponseBody
	@RequestMapping("insertGhShop")
	public void insertGhShop(GhShop ghShop, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		GhShop ghShop2 = services.findGhShopByPhone(ghShop.getPhone());
		int a = 0;
		System.out.println(ghShop2);
		if (ghShop2 == null) {
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			ghShop.setDates(format.format(date));
			a = services.insertGhShop(ghShop);
			System.out.println(ghShop);
		} else {
			a = 2;
		}
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(URLDecoder.decode(json, "utf-8"));
		pw.close();
	}

	// 分销
	@ResponseBody
	@RequestMapping("findFenxiao")
	public void findFenxiao(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Fenxiao fenxiao = services.findFenxiao();
		System.out.println(fenxiao);
		Gson gson = new Gson();
		String json = gson.toJson(fenxiao);
		PrintWriter pw = response.getWriter();
		pw.write(URLDecoder.decode(json, "utf-8"));
		pw.close();
	}

	// 等级
	@ResponseBody
	@RequestMapping("findGrade")
	public void findGrade(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Map map = services.findGrade();
		System.out.println(map);
		Gson gson = new Gson();
		String json = gson.toJson(map);
		PrintWriter pw = response.getWriter();
		pw.write(URLDecoder.decode(json, "utf-8"));
		pw.close();
	}

	// 修改分销
	@ResponseBody
	@RequestMapping("updateFenxiao")
	public void updateFenxiao(Fenxiao fenxiao, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		int a = services.updateFenxiao(fenxiao);
		System.out.println(a);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(URLDecoder.decode(json, "utf-8"));
		pw.close();
	}

	// 修改等级
	@ResponseBody
	@RequestMapping("updateMGrade")
	public void updateMGrade(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Map map = new HashMap<>();
		map.put("two", request.getParameter("two"));
		map.put("three", request.getParameter("three"));
		map.put("four", request.getParameter("four"));
		map.put("five", request.getParameter("five"));
		int a = services.updateMGrade(map);
		System.out.println(a);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(URLDecoder.decode(json, "utf-8"));
		pw.close();
	}

	// 审核供货商
	@ResponseBody
	@RequestMapping("updateGhShopStatus")
	public void updateGhShopStatus(GhShop ghShop, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		int a = services.updateGhShopStatus(ghShop);
		System.out.println(ghShop);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(URLDecoder.decode(json, "utf-8"));
		pw.close();
	}

	// 验证供货商
	@ResponseBody
	@RequestMapping("findGhShop")
	public void findGhShop(GhShop ghShop, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		GhShop shop = services.findGhShop(ghShop);
		if(shop!=null) {
		System.out.println(shop);
		Fenxiao fenxiao = new Fenxiao();
		fenxiao = services.findFenxiaoById(shop.getId());
		System.out.println(fenxiao);
		if(fenxiao==null) {
			Fenxiao fenxiao2 = new Fenxiao();
			fenxiao2.setGrade(0);
			fenxiao2.setOne(0);
			fenxiao2.setTwo(0);
			fenxiao2.setPpname(shop.getName());
			fenxiao2.setGhid(shop.getId());
			System.out.println(fenxiao2);
			int a = services.insertFenxiao(fenxiao2);
		}
		
		}
		System.out.println(ghShop);
		Gson gson = new Gson();
		String json = gson.toJson(shop);
		PrintWriter pw = response.getWriter();
		pw.write(URLDecoder.decode(json, "utf-8"));
		pw.close();
	}

	// 申请状态
	@ResponseBody
	@RequestMapping("findFenxiaoById")
	public void findFenxiaoById(int id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Fenxiao fenxiao = services.findFenxiaoById(id);
		Gson gson = new Gson();
		String json = gson.toJson(fenxiao);
		PrintWriter pw = response.getWriter();
		pw.write(URLDecoder.decode(json, "utf-8"));
		pw.close();
	}

	
	// 提现申请
	@ResponseBody
	@RequestMapping("insertCash")
	public void insertCash(Cash cash, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Date date = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		cash.setDates(f.format(date));
		System.out.println(cash);
		DecimalFormat dd = new DecimalFormat("#.00");
		int a = services.insertCash(cash);
		if(a>0) {
			int b = 0;
			while(b==0) {
				Users users = services.findUserByOpenid(cash.getOpenid());
				double moneys = Double.parseDouble(users.getMoney());
				Map map = new HashMap<>();
				map.put("money", dd.format(moneys-Double.parseDouble(cash.getMoney())));
				map.put("openid", cash.getOpenid());
				map.put("moneys", moneys);
				System.out.println(map);
				b =services.updateMoneys(map);
				System.out.println(b);
			}
			
		}
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	// 申请状态
	@ResponseBody
	@RequestMapping("updateCashState")
	public void updateCashState(Cash cash, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		int a = services.updateCashState(cash);
		Gson gson = new Gson();
		String json = gson.toJson(a);
		PrintWriter pw = response.getWriter();
		pw.write(URLDecoder.decode(json, "utf-8"));
		pw.close();
	}

	// 分页查询申请
	@ResponseBody
	@RequestMapping("findCashList")
	public void findCashList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		List list = new ArrayList<>();
		int id = new Integer(request.getParameter("id"));
		String openid = "%" + request.getParameter("openid") + "%";
		Map map = new HashMap();
		map.put("start", (id - 1) * 10);
		map.put("end", 10);
		map.put("openid", openid);
		list = services.findCashList(map);
		System.out.println(list);
		Gson gson = new Gson();
		String json = gson.toJson(list);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	// 提现数量
	@ResponseBody
	@RequestMapping("countCash")
	public void countCash(String openid, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Map maps = new HashMap<>();
		maps.put("openid", "%" + openid + "%");
		int a = services.countCash(maps);
		System.out.println(a);
		// 锟街讹拷锟斤拷页
		int page = 0;
		if (a % 10 == 0) {
			page = a / 10;
		} else {
			page = a / 10 + 1;
		}
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("count", a);
		map.put("page", page);
		Gson gson = new Gson();
		String json = gson.toJson(map);
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	// 对账单查询
	@ResponseBody
	@RequestMapping("insertPayDetail")
	public void insertPayDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("appid", "wxd3270e7769759ce7");
		String datas = request.getParameter("times");
		String dates = datas.replaceAll("-", "");
		parameters.put("mch_id", "1541631801");
		String times = String.valueOf(System.currentTimeMillis());
		// parameters.put("device_info", "");//微信支付分配的终端设备号，填写此字段，只下载该设备号 的对账单

		parameters.put("nonce_str", times);

		parameters.put("bill_date", dates);// 下载对账单的日期，格式：20140603，日期不可为当天。

		// bill_type:ALL返回当日所有订单信息,默认值SUCCESS返回当日成功支付的订单。REFUND，返回当日退款订单

		parameters.put("bill_type", "ALL");

		String sign = PayCommonUtil.createSign("UTF-8", parameters, "08dc51acad3502c6777c36962a8483a1");

		parameters.put("sign", sign);

		String reuqestXml = PayCommonUtil.getRequestXml(parameters);
		;

		String result = HttpUtil.postData("https://api.mch.weixin.qq.com/pay/downloadbill", reuqestXml);
		// String result=CommonUtil.httpsRequest(ConfigUtil.DOWNLOAD_BILL_URL,
		// "POST",reuqestXml);

		if (result.startsWith("<xml>")) {// 查询日期为当天时，错误信息提示日期无效

			// System.out.println(result);

			Gson gson = new Gson();
			String json = gson.toJson("0");
			System.out.println(json);
			PrintWriter pw = response.getWriter();
			pw.write(json);
			pw.close();

		} else {
			String tradeMsg = result.substring(result.indexOf("`"));
			String tradeInfo = tradeMsg.substring(0, tradeMsg.indexOf("总"));
			String tradeTotalMsg = tradeMsg.substring(tradeMsg.indexOf("总"));
			String tradeTotalInfo = tradeTotalMsg.substring(tradeTotalMsg.indexOf("`"));
			System.out.println(result);
			List<PayDetailBean> list = saveWeiXinRecord(result);
			Gson gson = new Gson();
			String json = gson.toJson(list);
			System.out.println(json);
			PrintWriter pw = response.getWriter();
			pw.write(json);
			pw.close();
		}

	}

	// 插入数据库
	public List saveWeiXinRecord(String Str) {
		System.out.println(Str.replaceAll("(\\\r\\\n|\\\r|\\\n|\\\n\\\r)", ""));
		int i = Str.indexOf("`");
		int j = Str.indexOf("总");
		System.out.println(i);
		System.out.println(j);
		String substring = Str.substring(i, j - 2).replaceAll("(\\\r\\\n|\\\r|\\\n|\\\n\\\r)", "");
		String[] temp = substring.split(",``");
		System.out.println(temp.length);
		// String[] payment = temp[0].replace("`", "").split(",");
		ArrayList<PayDetailBean> list = new ArrayList<PayDetailBean>();
		for (int k = 0; k < temp.length; k++) {
			String[] payment = temp[k].replace("`", "").split(",");
			PayDetailBean bean = new PayDetailBean();
			for (int p = 0; p < payment.length; p++) {
				bean.setTrade_time(payment[0]);
				bean.setAppid(payment[1]);
				bean.setMch_id(payment[2]);
				bean.setMch_appid(payment[3]);
				bean.setDevice_info(payment[4]);
				bean.setTransaction_id(payment[5]);
				bean.setOut_trade_no(payment[6]);
				bean.setOpenid(payment[7]);
				bean.setTrade_type(payment[8]);
				bean.setTrade_status(payment[9]);
				bean.setPay_bank(payment[10]);
				bean.setMoney_type(payment[11]);
				bean.setOrder_pay(payment[12]);
				bean.setVoucher_amount(payment[13]);
				bean.setRefund_number(payment[14]);
				bean.setOut_refund_no(payment[15]);
				bean.setRefund_amount(payment[16]);
				bean.setRefund_amount_voucher(payment[17]);
				bean.setRefunds_type(payment[18]);
				bean.setRefunds_status(payment[19]);
				bean.setCommodity_name(payment[20]);
				bean.setData_packet(payment[21]);
				bean.setService_charge(payment[22]);
				bean.setRate(payment[23]);
				bean.setOrder_amount(payment[24]);
				bean.setApplication_refund_amount(payment[25]);
			}
			list.add(bean);
		}

		/*
		 * Gson gson2 = new Gson(); String str = gson2.toJson(list);
		 * System.out.println(str);
		 */
		return list;
	}

}
