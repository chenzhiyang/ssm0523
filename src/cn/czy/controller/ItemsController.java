package cn.czy.controller;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.czy.pojo.Items;
import cn.czy.service.ItemsService;
import cn.czy.service.ItemsServiceImpl;
import cn.czy.vo.QueryVo;

@Controller
//窄化请求映射：为防止你和你队友在controller方法起名时重名，使用相当于在url中多加了一层目录，防止重名
//当前访问地址为http://localhost:8080/ssm0523-1/items/list.action
@RequestMapping("/items")
public class ItemsController {
	
	@Autowired
	private ItemsService itemsService;
	
	@RequestMapping("/list")
	public ModelAndView itemsList() throws Exception{
		List<Items> list=itemsService.list();
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.addObject("itemList", list);
		modelAndView.setViewName("itemList");
		return modelAndView;
	}
	
	
	/**
	 * springmvc默认支持的参数类型：可加可不加
	 * @param request：
	 * @param response
	 * @param session
	 * @param model
	 * 
	 * 通过@PathVariable可以接收url中传入的参数
	 * @RequestMapping("/itemEdit/{id}")中接收参数使用大括号加上变量名称，@PathVariable中变量名称要和@RequestMapping中变量名称相同
	 * @return
	 * @throws Exception
	 */
	//@RequestMapping(value="/itemEdit",method=RequestMethod.GET) 限定请求方式
	@RequestMapping("/itemEdit/{id}")
	public String itemEdit(@PathVariable("id") Integer id,HttpServletRequest request,HttpServletResponse response
			,HttpSession session,Model model) throws Exception{
		//String id=request.getParameter("id");
		Items item=itemsService.findItemById(id);
		
		
		//Model模型:模型中放入了返回页面的数据
		//Model底层其实就是用的request域传递数据，但是对request域进行了扩展
		model.addAttribute("item",item);
		
		//如果springMVC方法返回一个简单的string字符串，则springmvc就会认为这个字符串是页面的名称
		return  "editItem";
	}
	
	//springMvc可以直接接受基本数据类型，包括spring。springmvc可以自动类型转换
	//controller方法接受的参数的变量名称必须要等于页面上input框的name属性值
	//public String updateitem(Integer id,String name,float price,String detail) throws Exception{
	
	//springmvc可以直接接收pojo类型，要求页面上input框的name属性名称必须等于pojo属性名称
	@RequestMapping("/updateitem")
	public String updateitem(MultipartFile pictureFile,Items items,Model model) throws Exception{
		//上传图片
		//1.获取图片完整名称
		String fileStr=pictureFile.getOriginalFilename();
		
		//2.使用随机生成的字符串+原图片扩展名组成的新的图片名称，防止图片重名
		String newFileName=UUID.randomUUID().toString()+fileStr.substring(fileStr.lastIndexOf("."));
		//3.将图片保存到硬盘
		pictureFile.transferTo(new File("E:\\iamge\\"+newFileName));
		//4.将图片名称保存到数据库
		items.setPic(newFileName);
		
		//items.setCreatetime(new Date());
		itemsService.updateItems(items);
		//return "success";
		
		//指定返回页面（如果controller方法返回值为void，则不走springmvc组件，所以要写页面完整路径名称）
		//request.getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(request, response);
		
		//重定向：浏览器中rul改变，request域中数据不可以带到重定向的方法中
		//springmvc中返回字符串凡是以redirect开头的都是重定向
		//重定向request域数据不能带到下一个方法中，但是Model可以，虽然Model也是用request域，但它做了扩展。
		//model.addAttribute("id", items.getId()); 
		return "redirect:itemEdit/"+items.getId();
		
		
		//请求转发：浏览器url不变，request域中数据可以带到转发后的方法中
		//springmvc中请求转发：返回字符串以forward：开头的都是请求转发
		//后面forward：itemEdit.action表示相对路径，相对于当前目录，即为当前类上面指定的items目录。在当前目录下可以使用相对路径随意跳转到某个方法中
		//后面forward：/items/itemEdit.action 路径中以/开头的为绝对路径，绝对路径从项目名后面开始算 
		//model.addAttribute("id", items.getId());  
		//return "forward:itemEdit.action";
		
	}
	
	//如果Contrller中接收的是Vo，则页面上input的name属性值要等于vo中属性.属性.属性...
	@RequestMapping("/search")
	public String search(QueryVo vo) throws Exception{
		System.out.println(vo);
		return "";
	}
	
	//导入jackson的jar包，在controller的方法中可以使用@RequestBody，让springmvc将json格式的字符串自动转换成java的pojo
	//页面Json的key要等于 java中pojo的属性名称
	//controller方法返回pojo类型的对象并且使用@RequestBody注解，springmvc会自动将pojo对象转换成json格式的字符串
	@RequestMapping("/sentJson")
	@ResponseBody
	public Items json(@RequestBody Items items) throws Exception{
		System.out.println(items);
		return items;
	}
	
}
