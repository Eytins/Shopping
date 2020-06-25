package com.zte.shopping.entity;

public class Product 
{
	private Integer productId;
	
	private String productNo;
	
	private String name;
	
	private Double price;
	
	private String image;
	
	private ProductType productType;
	
	public Product()
	{
		super();
	}
	
	public Integer getProductId() 
	{
		return productId;
	}
	
	public void setProductId(Integer productId)
	{
		this.productId = productId;
	}
	
	public String getProductNo() 
	{
		return productNo;
	}
	public void setProductNo(String productNo)
	{
		this.productNo = productNo;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public Double getPrice() 
	{
		return price;
	}
	
	public void setPrice(Double price)
	{
		this.price = price;
	}
	
	public String getImage()
	{
		return image;
	}
	
	public void setImage(String image) 
	{
		this.image = image;
	}
	
	public ProductType getProductType() 
	{
		return productType;
	}
	
	public void setProductType(ProductType productType) 
	{
		this.productType = productType;
	}
}
