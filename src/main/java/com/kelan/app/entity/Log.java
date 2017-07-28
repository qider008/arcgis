package com.kelan.app.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.kelan.common.utils.StringLocalUtils;

/**
* @author 赵奎东 E-mail:285713138@qq.com
* @version 创建时间：2017年7月27日 下午2:50:25
* 类说明
*/
@Table(name="sys_log")
@Entity
public class Log implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator="sys_uuid")
	@GenericGenerator(name="sys_uuid",strategy="uuid")
	@Column(name="id",length=32,insertable=true,unique=true,nullable=false,updatable=false)
	private String id;
	@Column(length=2)
	private String type; 		// 日志类型（1：接入日志；2：错误日志）
	@Column(length=255)
	private String title;		// 日志标题
	@Column(length=255)
	private String remoteAddr; 	// 操作用户的IP地址
	@Column(length=255)
	private String requestUri; 	// 操作的URI
	@Column(length=5)
	private String method; 		// 操作的方式
	@Column(length=1000)
	private String params; 		// 操作提交的数据
	@Column(length=255)
	private String userAgent;	// 操作用户代理信息
	@Column(length=255)
	private String exception; 	// 异常信息
	@Column(length=64,name="create_by")
	private String createBy;
	@Column(name="create_date")
	private Date createDate;
	
	@Transient
	private Date beginDate;		// 开始日期
	@Transient
	private Date endDate;		// 结束日期
	
	// 日志类型（1：接入日志；2：错误日志）
	@Transient
	public static final String TYPE_ACCESS = "1";
	@Transient
	public static final String TYPE_EXCEPTION = "2";
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRemoteAddr() {
		return remoteAddr;
	}
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}
	public String getRequestUri() {
		return requestUri;
	}
	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public static String getTypeAccess() {
		return TYPE_ACCESS;
	}
	public static String getTypeException() {
		return TYPE_EXCEPTION;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setParams(Map paramMap){
		if (paramMap == null){
			return;
		}
		StringBuilder params = new StringBuilder();
		for (Map.Entry<String, String[]> param : ((Map<String, String[]>)paramMap).entrySet()){
			params.append(("".equals(params.toString()) ? "" : "&") + param.getKey() + "=");
			String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
			params.append(StringLocalUtils.abbr(StringLocalUtils.endsWithIgnoreCase(param.getKey(), "password") ? "" : paramValue, 100));
		}
		this.params = params.toString();
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
