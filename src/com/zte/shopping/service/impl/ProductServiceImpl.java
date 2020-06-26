package com.zte.shopping.service.impl;

import com.zte.shopping.constant.DictConstant;
import com.zte.shopping.constant.StatusConstant;
import com.zte.shopping.dao.IProductDao;
import com.zte.shopping.dao.ISequenceDao;
import com.zte.shopping.entity.Product;
import com.zte.shopping.entity.ProductType;
import com.zte.shopping.entity.Sequence;
import com.zte.shopping.service.IProductService;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import param.ProductParameter;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Eytins
 */

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ProductServiceImpl implements IProductService {
    @Autowired
    private IProductDao productDao;

    @Autowired
    private ISequenceDao sequenceDao;

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Product> findAll() {
        List<Product> productList = productDao.selectAll();

        return productList;
    }


    @Override
    public void addProduct(CommonsMultipartFile file, String productNo, String productName, String price, String typeId, HttpSession session) throws FileUploadException {
        Product product = new Product();
        product.setProductNo(productNo);
        product.setName(productName);
        product.setPrice(Double.parseDouble(price));

        ProductType type = new ProductType();
        type.setId(Integer.parseInt(typeId));
        product.setProductType(type);

        /**
         标识符  + 年月日  + 序列号

         标识符:例如:商品--SP
         年月日:年月日--20180106
         序列号:000001--999999
         第一个编号序列号为000001
         当编号达到了999999,则下一个编号重置为000001
         序列号信息保存在sys_sequence表中

         id
         name		标识符		SP
         value		同一个标识符只对应一个值
         假如当前的SP值为0000001
         如果新增一个新的商品,则同步修改sequence表中的数据,将其值改为000002
         */
        // DictConstant.PRODUCT_NO_PREFIX表示 商品编号前缀 :SP
        Sequence sequence = sequenceDao.selectByName(DictConstant.PRODUCT_NO_PREFIX);

        if (sequence == null) {
            // 如果没有查找到对应的Sequence信息  则表示此时尚未使用商品编号
            // 初始化商品编号的序列号值为最小,即000001
            Sequence sequ = new Sequence();

            // 设置 商品编号前缀: SP
            sequ.setName(DictConstant.PRODUCT_NO_PREFIX);

            // 设置 商品编号的序列号最小值为000001
            sequ.setValue(DictConstant.PRODUCT_NO_SEQUENCE_MIN);

            sequenceDao.insertSequence(sequ);

            //  标识符  + 年月日 + 序列号
            product.setProductNo(DictConstant.PRODUCT_NO_PREFIX + new SimpleDateFormat("yyyyMMdd").format(new Date()) + sequ.getValue());
        } else {
            String value = sequence.getValue();

            // 若当前的序列号达到了最大值,则初始化设置为最小值,从头开始
            if (DictConstant.PRODUCT_NO_SEQUENCE_MAX.equals(value)) {
                value = DictConstant.PRODUCT_NO_SEQUENCE_MIN;
            } else {
                value = String.format("%06d", Integer.parseInt(value) + 1);
            }

            sequenceDao.updateSquenceValue(DictConstant.PRODUCT_NO_PREFIX, value);
        }

        String path        = "/upload/" + new SimpleDateFormat("yyyyMMdd").format(new Date());
        String cp          = session.getServletContext().getRealPath(path);
        String orifileName = file.getOriginalFilename(); // 原始文件名

        product.setImage(path + "/" + orifileName);

        // 先做添加商品
        productDao.insertProduct(product);

        // 上传商品图片到服务器
        File f = new File(cp);
        f.mkdirs();

        try {
            file.transferTo(new File(cp, orifileName));
        } catch (IllegalStateException e) {
            // org.apache.commons.fileupload.FileUploadException 不是自定义异常
            throw new FileUploadException("文件上传出错", e);
        } catch (IOException e) {
            throw new FileUploadException("文件上传出错", e);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Product findById(String id) {
        return productDao.selectById(Integer.parseInt(id));
    }

    /**
     * 修改商品信息
     */
    @Override
    public void modifyProduct(CommonsMultipartFile file, String productId,
                              String name, String price, String typeId, HttpSession session) throws FileUploadException {
        Product product = new Product();
        product.setProductId(Integer.parseInt(productId));
        product.setName(name);
        product.setPrice(Double.parseDouble(price));

        ProductType type = new ProductType();
        type.setId(Integer.parseInt(typeId));

        product.setProductType(type);

        String path             = "/upload/" + new SimpleDateFormat("yyyyMMdd").format(new Date());
        String cp               = session.getServletContext().getRealPath(path);
        String originalFilename = file.getOriginalFilename();

        // 解决当会员在修改商品时  没有 修改商品的图片的bug
        String image = "";
        if (originalFilename != null && "".equals(originalFilename)) {
            Product pro = productDao.selectById(Integer.parseInt(productId));

            if (pro != null) {
                String imageStr = pro.getImage();

                image = path + "/" + imageStr.substring(imageStr.lastIndexOf("/") + 1, imageStr.length());
            }
        } else {
            image = path + "/" + originalFilename;
        }


        product.setImage(image);

        productDao.updateProduct(product);

        File f = new File(cp);
        f.mkdirs();

        try {
            file.transferTo(new File(cp, originalFilename));
        } catch (IllegalStateException e) {
            throw new FileUploadException("文件上传出错", e);
        } catch (IOException e) {
            throw new FileUploadException("文件上传出错", e);
        }


    }

    /**
     * 根据 id 删除商品信息
     */
    public void removeById(String id) {
        productDao.deleteById(Integer.parseInt(id));
    }

    /**
     * 根据条件组合查询商品列表(支持模糊查询)
     * param	可选
     * minPrice	最低价格
     * maxPrice	最高价格
     * productTypeId	商品类型
     * status		商品类型的状态		必带条件
     * List<Product>  符合条件的商品列表
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Product> findProductFuzzyParamList(ProductParameter parameter) {
        // StatusConstant.PRODUCT_TYPE_STATUS_ENABLE 商品类型的状态为启用状态,值为1
        parameter.setStatus(StatusConstant.PRODUCT_TYPE_STATUS_ENABLE);

        List<Product> productList = productDao.selectByParamList(parameter);

        return productList;
    }

}
