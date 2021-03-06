package tw.com.MoPet.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import tw.com.MoPet.model.Cart;
import tw.com.MoPet.model.CartItems;
import tw.com.MoPet.model.Product;
import tw.com.MoPet.model.member;
import tw.com.MoPet.service.CartService;
import tw.com.MoPet.service.CartitemsService;
import tw.com.MoPet.service.ProductService;
import tw.com.MoPet.service.memberService;

@Controller
public class CartController {

	@Autowired
	private ProductService pService;

	@Autowired
	private CartitemsService ciService;

	@Autowired
	private CartService cService;

	@Autowired
	private memberService mService;

	@GetMapping("minus/cartItem/{id}")
	public String minusItems(@PathVariable Integer id, HttpSession session) {

		if (session.getAttribute("loginOK") == null) {
			String prePage = "redirect:/into/cart";
			session.setAttribute("PrePage", prePage);
			return "redirect:/login";

		} else {
			int memId = Integer.parseInt(session.getAttribute("cart_ID").toString());
			Optional<Cart> cart = cService.findByMemberId(memId);
			Integer cartId = cart.get().getCartId().intValue();

			CartItems item = ciService.findItemByTwoKeys(id, cartId);
			item.setCartItemsAmount(item.getCartItemsAmount() - 1);

			if (item.getCartItemsAmount() != 0) {
				ciService.insertCartItems(item);
			} else {
				ciService.deleteItem(item.getCartItemsId());
			}
			
//			boolean haveOrNot;
//			Integer memId = Integer.parseInt(session.getAttribute("cart_ID").toString());
//			if(memId!=null) {
//			Cart getCart =  cartService.findBymIdAndcStatus(memId, false);
//			if(getCart!=null) {
//				haveOrNot=true;
//				session.setAttribute("haveOrNot", haveOrNot);
//			}else {
//				haveOrNot=false;
//				session.setAttribute("haveOrNot", haveOrNot);
//			}}
			
			
			return "redirect:/into/cart";
		}
	}

	@GetMapping("add/cartItem/{id}")
	public String addItems(@PathVariable Integer id, HttpSession session) {
		if (session.getAttribute("loginOK") == null) {
			String prePage = "redirect:/into/cart";
			session.setAttribute("PrePage", prePage);
			return "redirect:/login";
		} else {
			int memId = Integer.parseInt(session.getAttribute("cart_ID").toString());
			Optional<Cart> cart = cService.findByMemberId(memId);

			Integer cartId = cart.get().getCartId().intValue();
			CartItems item = ciService.findItemByTwoKeys(id, cartId);
			item.setCartItemsAmount(item.getCartItemsAmount() + 1);
			ciService.insertCartItems(item);

			return "redirect:/into/cart";
		}
	}

	@GetMapping("delete/cartItem/{id}")
	public String deleteItems(@PathVariable Integer id, HttpSession session) {
		if (session.getAttribute("loginOK") == null) {
			String prePage = "redirect:/into/cart";
			session.setAttribute("PrePage", prePage);
			return "redirect:/login";
		} else {
			int memId = Integer.parseInt(session.getAttribute("cart_ID").toString());
			Optional<Cart> cart = cService.findByMemberId(memId);
			Integer cartId = cart.get().getCartId().intValue();

			CartItems items = ciService.findItemByTwoKeys(id, cartId);

			ciService.deleteItem(items.getCartItemsId());

//		ciService.deleteItemByTwoKeys(id,cartId);
			return "redirect:/into/cart";
		}
	}

	@GetMapping("add/cartItems/{id}")
	public String addCartList(@PathVariable Integer id, HttpSession session,Model model) {

		if (session.getAttribute("loginOK") == null) {
			String prePage = "redirect:/shop/products";
			session.setAttribute("PrePage", prePage);
			return "redirect:/login";
		}

		else {

			//????????????ID
			//????????????ID??????cart
			
			int memId = Integer.parseInt(session.getAttribute("cart_ID").toString());
//			Optional<Cart> cart = cService.findByMemberId(memId);
			Cart tempCart = null;

			Cart getCart = cService.findBymIdAndcStatus(memId, false);

			// ??????getCart???null
			if (getCart != null) {
				
				//productID???cartid?????????????????????????????????null??????????????????1
				CartItems item = ciService.findItemByTwoKeys(id,getCart.getCartId());
				if (item != null) {
					item.setCartItemsAmount(item.getCartItemsAmount() + 1);
					ciService.insertCartItems(item);
					
				} else {
					// ????????????????????????new??????items
					CartItems items = new CartItems();
					// ?????????????????????????????????
					Product getProduct = pService.getById(id);

					if (getProduct != null) {
						items.setpId(getProduct);
						items.setCartId(getCart);
						// ????????????1
						items.setCartItemsAmount(1);
						// insert?????????????????????
						ciService.insertCartItems(items);
					}
				}
				//??????????????????getCart???
			} else {

				Cart newCart = new Cart();
				member tempMember = mService.findById(memId);

				// ??????member????????????????????????member??????
				if (tempMember != null) {
					newCart.setFkMemberId(tempMember);
					// ?????????????????????????????????????????????null??????
					tempCart = cService.insertCart(newCart);

					// ????????????
					// ?????????status?????????false???????????????????????????
					// new??????items
					CartItems items = new CartItems();
					Product getProduct = pService.getById(id);

					// ????????????
					items.setpId(getProduct);
					items.setCartId(tempCart);
					// ????????????1
					items.setCartItemsAmount(1);
					// insert?????????????????????
					ciService.insertCartItems(items);
				}
			}
			
			return "redirect:/shop/products";
		}
	}


	@GetMapping("into/cart")
	public ModelAndView seeCartItems(ModelAndView mvc, HttpSession session) {

		if (session.getAttribute("loginOK") == null) {
			String prePage = "redirect:/shop/products";
			session.setAttribute("PrePage", prePage);
			mvc.setViewName("redirect:/login");
		} else {
			int memId = Integer.parseInt(session.getAttribute("cart_ID").toString());
			boolean haveOrNot=false;
			Integer listSize=0;
			
			Object object = session.getAttribute("cart_ID");
			if(object!=null) {
				Integer membId=(Integer)object;
				Cart getCart =  cService.findBymIdAndcStatus(membId, false);
				
				if(getCart!=null) {
				
				List<CartItems> tempListItems=ciService.findItemByCart(getCart.getCartId());
				
				if(tempListItems.size()!=0) {
					haveOrNot=true;
					
					for(CartItems items:tempListItems) {
						listSize+=items.getCartItemsAmount();
					}
					
					session.setAttribute("listSize", listSize);
					session.setAttribute("haveOrNot", haveOrNot);
					
				}else if(tempListItems.size()==0) {
					listSize=0;
					haveOrNot=false;
					session.removeAttribute("listSize");
					session.setAttribute("haveOrNot", haveOrNot);
//					session.removeAttribute("haveOrNot");
				}
			}else {
				listSize=0;
				session.removeAttribute("listSize");
				haveOrNot=false;
				session.setAttribute("haveOrNot", haveOrNot);
			}}
			
			Optional<Cart> cart = cService.findByMemberId(memId);

			if (cart.isEmpty()) {
				mvc.setViewName("cartItemsEmpty");
			} else {
				List<CartItems> cartList = ciService.findItemByCart(cart.get().getCartId());
				if (cartList.isEmpty() || cart.get().isCartStatus() == true) {
					mvc.setViewName("cartItemsEmpty");
				} else if (!cart.isEmpty() && cart.get().isCartStatus() == false) {
					System.out.println("??????memberid?????????????????? cart Id: " + cart.get().getCartId());
					List<CartItems> productList = ciService.findItemByCart(cart.get().getCartId());
					mvc.getModel().put("productList", productList);
					mvc.setViewName("cartItems");
				}
			}
		}
		return mvc;
	}
}
