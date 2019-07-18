package recycle.servlet;
 
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import recycle.bean.Product;
import recycle.bean.ProductImage;
import recycle.dao.ProductImageDAO;
import recycle.util.ImageUtil;
import recycle.util.Page;
 
public class ProductImageServlet extends BaseBackServlet {
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        InputStream is = null;
        Map<String,String> params = new HashMap<>();
 
        is = parseUpload(request, params);     
        
        int pid = Integer.parseInt(params.get("pid"));
        Product p =productDAO.get(pid);
         
        ProductImage pi = new ProductImage();      
        pi.setProduct(p);
        productImageDAO.add(pi);
         
        String fileName = pi.getId()+ ".jpg";
        String imageFolder;
        String imageFolder_small=null;
        String imageFolder_middle=null;
        
        imageFolder= request.getSession().getServletContext().getRealPath("img/productSingle");
        imageFolder_small= request.getSession().getServletContext().getRealPath("img/productSingle_small");
        imageFolder_middle= request.getSession().getServletContext().getRealPath("img/productSingle_middle");
        File f = new File(imageFolder, fileName);
        f.getParentFile().mkdirs();
         
        try {
            if(null!=is && 0!=is.available()){
                try(FileOutputStream fos = new FileOutputStream(f)){
                    byte b[] = new byte[1024 * 1024];
                    int length = 0;
                    while (-1 != (length = is.read(b))) {
                        fos.write(b, 0, length);
                    }
                    fos.flush();
                    BufferedImage img = ImageUtil.change2jpg(f);
                    ImageIO.write(img, "jpg", f);      
                     
                    File f_small = new File(imageFolder_small, fileName);
                    File f_middle = new File(imageFolder_middle, fileName);
                    ImageUtil.resizeImage(f, 56, 56, f_small);
                    ImageUtil.resizeImage(f, 217, 190, f_middle);
                    
                         
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 
        return "@admin_productImage_list?pid="+p.getId();
    }
 
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        ProductImage pi = productImageDAO.get(id);
        productImageDAO.delete(id);
         
        String imageFolder_single= request.getSession().getServletContext().getRealPath("img/productSingle");
        String imageFolder_small= request.getSession().getServletContext().getRealPath("img/productSingle_small");
        String imageFolder_middle= request.getSession().getServletContext().getRealPath("img/productSingle_middle");
         
        File f_single =new File(imageFolder_single,pi.getId()+".jpg");
        f_single.delete();
        File f_small =new File(imageFolder_small,pi.getId()+".jpg");
        f_small.delete();
        File f_middle =new File(imageFolder_middle,pi.getId()+".jpg");
        f_middle.delete();
             
        
        return "@admin_productImage_list?pid="+pi.getProduct().getId();
    }
 
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;       
    }
 
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;   
    }
 
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        int pid = Integer.parseInt(request.getParameter("pid"));
        Product p =productDAO.get(pid);
        List<ProductImage> pisSingle = productImageDAO.list(p);
        request.setAttribute("p", p);
        request.setAttribute("pisSingle", pisSingle);
         
        return "admin/listProductImage.jsp";
    }
}