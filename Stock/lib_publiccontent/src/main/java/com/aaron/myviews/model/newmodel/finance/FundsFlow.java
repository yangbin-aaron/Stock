package com.aaron.myviews.model.newmodel.finance;


/**
 * 资金流水,资金流水列表元素的模型类
 * @author bvin
 */
public class FundsFlow {
    
    private Integer id;//流水id
    private Integer type;//流转类型（1;//进入，-1;//流出）
    private Integer flowway;//流转方式
    private String intro;//流转方式说明
    private String curflowAmt;//当前流转资金
    private Integer status;//状态（1;//已处理，-1;//未处理） 
    private String createDate;//创建时间
    
    public Integer getId() {
        return id;
    }
    public Integer getType() {
        return type;
    }
    public Integer getFlowway() {
        return flowway;
    }
    public String getIntro() {
        return intro;
    }
    public String getCurflowAmt() {
        return curflowAmt;
    }
    public Integer getStatus() {
        return status;
    }
    public String getCreateDate() {
        return createDate;
    }
    @Override
    public String toString() {
        return "FundsFlow [id=" + id + ", type=" + type + ", flowway=" + flowway + ", intro=" + intro
                + ", curflowAmt=" + curflowAmt + ", status=" + status + ", createDate=" + createDate + "]";
    }
    
}
