package com.bx.jz.jy.jybx.bean;

import java.io.Serializable;

/**
* ClassName: Ingredients <br/>
* Description:  <br/>
*/
public class Ingredients implements Serializable{

	private boolean isClick = false;

	public boolean isClick() {
		return isClick;
	}

	public void setClick(boolean click) {
		isClick = click;
	}

	private int freshness;

	public int getFreshness() {
		return freshness;
	}

	public void setFreshness(int freshness) {
		this.freshness = freshness;
	}

	private Long userId;

	/**
	 * 食材id
	 */
    private Long ingredientsId;
	/**
	 * 图片URL
	 */
    private String imgUrl;
	/**
	 * 食材名称
	 */
    private String ingredientsName;
	/**
	 * 保质期时间（保质期时间的时间戳）
	 */
    private Double shelfLifeTime;
	/**
	 * 保质期剩余时间（时间戳）
	 */
    private Long shelfLifeRemaining;
	/**
	 * 所属位置(1=冷藏，2=变温，3=冷冻)
	 */
    private Integer subordinatePosition;
	/**
	 * 所属分类
	 */
    private Long classificationId;
	/**
	 * 食材分量
	 */
    private String foodComponent;
	/**
	 * 分量单位
	 */
    private String componentUnit;
	/**
	 * 对应知识图谱id
	 */
    private Long knowledgeGraphId;
	/**
	 * 添加方式（1=图片识别添加，2=手动添加）
	 */
    private Integer addWay;
	/**
	 * 添加时间
	 */
    private String addTime;
	/**
	 * 修改时间
	 */
    private String ingredientsUpdateTime;
	/**
	 * 是否删除(1=正常，2=删除)
	 */
    private Integer isDelete;

	public Ingredients() {
    }

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 食材id
	 */
	public Ingredients setIngredientsId(Long ingredientsId){
		this.ingredientsId = ingredientsId;
		return this;
	}

	/**
	 * 食材id
	 */
	public Long getIngredientsId(){
		return this.ingredientsId;
	}

	/**
	 * 图片URL
	 */
	public Ingredients setImgUrl(String imgUrl){
		this.imgUrl = imgUrl;
		return this;
	}

	/**
	 * 图片URL
	 */
	public String getImgUrl(){
		return this.imgUrl;
	}

	/**
	 * 食材名称
	 */
	public Ingredients setIngredientsName(String ingredientsName){
		this.ingredientsName = ingredientsName;
		return this;
	}

	/**
	 * 食材名称
	 */
	public String getIngredientsName(){
		return this.ingredientsName;
	}

	/**
	 * 保质期时间（保质期时间的时间戳）
	 */
	public Ingredients setShelfLifeTime(Double shelfLifeTime){
		this.shelfLifeTime = shelfLifeTime;
		return this;
	}

	/**
	 * 保质期时间（保质期时间的时间戳）
	 */
	public Double getShelfLifeTime(){
		return this.shelfLifeTime;
	}

	/**
	 * 保质期剩余时间（时间戳）
	 */
	public Ingredients setShelfLifeRemaining(Long shelfLifeRemaining){
		this.shelfLifeRemaining = shelfLifeRemaining;
		return this;
	}

	/**
	 * 保质期剩余时间（时间戳）
	 */
	public Long getShelfLifeRemaining(){
		return this.shelfLifeRemaining;
	}

	/**
	 * 所属位置(1=冷藏，2=变温，3=冷冻)
	 */
	public Ingredients setSubordinatePosition(Integer subordinatePosition){
		this.subordinatePosition = subordinatePosition;
		return this;
	}

	/**
	 * 所属位置(1=冷藏，2=变温，3=冷冻)
	 */
	public Integer getSubordinatePosition(){
		return this.subordinatePosition;
	}

	/**
	 * 所属分类
	 */
	public Ingredients setClassificationId(Long classificationId){
		this.classificationId = classificationId;
		return this;
	}

	/**
	 * 所属分类
	 */
	public Long getClassificationId(){
		return this.classificationId;
	}

	/**
	 * 食材分量
	 */
	public Ingredients setFoodComponent(String foodComponent){
		this.foodComponent = foodComponent;
		return this;
	}

	/**
	 * 食材分量
	 */
	public String getFoodComponent(){
		return this.foodComponent;
	}

	/**
	 * 分量单位
	 */
	public Ingredients setComponentUnit(String componentUnit){
		this.componentUnit = componentUnit;
		return this;
	}

	/**
	 * 分量单位
	 */
	public String getComponentUnit(){
		return this.componentUnit;
	}

	/**
	 * 对应知识图谱id
	 */
	public Ingredients setKnowledgeGraphId(Long knowledgeGraphId){
		this.knowledgeGraphId = knowledgeGraphId;
		return this;
	}

	/**
	 * 对应知识图谱id
	 */
	public Long getKnowledgeGraphId(){
		return this.knowledgeGraphId;
	}

	/**
	 * 添加方式（1=图片识别添加，2=手动添加）
	 */
	public Ingredients setAddWay(Integer addWay){
		this.addWay = addWay;
		return this;
	}

	/**
	 * 添加方式（1=图片识别添加，2=手动添加）
	 */
	public Integer getAddWay(){
		return this.addWay;
	}

	/**
	 * 添加时间
	 */
	public Ingredients setAddTime(String addTime){
		this.addTime = addTime;
		return this;
	}

	/**
	 * 添加时间
	 */
	public String getAddTime(){
		return this.addTime;
	}

	/**
	 * 修改时间
	 */
	public Ingredients setIngredientsUpdateTime(String ingredientsUpdateTime){
		this.ingredientsUpdateTime = ingredientsUpdateTime;
		return this;
	}

	/**
	 * 修改时间
	 */
	public String getIngredientsUpdateTime(){
		return this.ingredientsUpdateTime;
	}

	/**
	 * 是否删除(1=正常，2=删除)
	 */
	public Ingredients setIsDelete(Integer isDelete){
		this.isDelete = isDelete;
		return this;
	}

	/**
	 * 是否删除(1=正常，2=删除)
	 */
	public Integer getIsDelete(){
		return this.isDelete;
	}

	@Override
	public String toString() {
		return "Ingredients{" +
				"isClick=" + isClick +
				", freshness=" + freshness +
				", userId=" + userId +
				", ingredientsId=" + ingredientsId +
				", imgUrl='" + imgUrl + '\'' +
				", ingredientsName='" + ingredientsName + '\'' +
				", shelfLifeTime=" + shelfLifeTime +
				", shelfLifeRemaining=" + shelfLifeRemaining +
				", subordinatePosition=" + subordinatePosition +
				", classificationId=" + classificationId +
				", foodComponent='" + foodComponent + '\'' +
				", componentUnit='" + componentUnit + '\'' +
				", knowledgeGraphId=" + knowledgeGraphId +
				", addWay=" + addWay +
				", addTime='" + addTime + '\'' +
				", ingredientsUpdateTime='" + ingredientsUpdateTime + '\'' +
				", isDelete=" + isDelete +
				'}';
	}
}