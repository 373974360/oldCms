package com.yinhai.model;

import java.io.Serializable;

/**
 * @Description: 银海调查问卷列表返回对象
 * @User: Administrator
 * @Date: 2018/5/7 0007
 */
public class JiSuanQiVo implements Serializable {

    private String calculateType;//贷款计算方法1：等额本息法 2：等额本金
    private String syRate;//商业贷款利率
    private String gjjTate;//公积金利率
    private String syInterestAvg;//商业性贷款月均本息
    private String syInterestSum;//商业性贷款累计本息
    private String gjjInterestAvg;//公积金贷款月均本息
    private String gjjInterestSum;//公积金贷款累计本息
    private String avgDiff;//还款比较月均本息
    private String sumDiff;//还款比较累计本息

    private String gjjRepayMonth;//公积金贷款月还款额
    private String syRepayMonth;//商业贷款月还款额
    private String syLoanInterestSum;//商业贷款累计利息(元)
    private String gjjLoanInterestSum;//公积金贷款累计利息(元)
    private String syPrincipalInterestSum;//商业贷款累计本息(元)
    private String gjjPrincipalInterestSum;//公积金贷款累计本息(元)
    private String repaySumMonth;//合计月还款额
    private String IntersetSum;//合计累计利息(元)
    private String principalInterestSum;//合计累计本息(元)


    private String maxYear;//最高贷款年限（年）
    private String gjjLoan;//公积金贷款（元）
    private String syLoan;//商业性贷款（元）

    public String getCalculateType() {
        return calculateType;
    }

    public void setCalculateType(String calculateType) {
        this.calculateType = calculateType;
    }

    public String getSyRate() {
        return syRate;
    }

    public void setSyRate(String syRate) {
        this.syRate = syRate;
    }

    public String getGjjTate() {
        return gjjTate;
    }

    public void setGjjTate(String gjjTate) {
        this.gjjTate = gjjTate;
    }

    public String getSyInterestAvg() {
        return syInterestAvg;
    }

    public void setSyInterestAvg(String syInterestAvg) {
        this.syInterestAvg = syInterestAvg;
    }

    public String getSyInterestSum() {
        return syInterestSum;
    }

    public void setSyInterestSum(String syInterestSum) {
        this.syInterestSum = syInterestSum;
    }

    public String getGjjInterestAvg() {
        return gjjInterestAvg;
    }

    public void setGjjInterestAvg(String gjjInterestAvg) {
        this.gjjInterestAvg = gjjInterestAvg;
    }

    public String getGjjInterestSum() {
        return gjjInterestSum;
    }

    public void setGjjInterestSum(String gjjInterestSum) {
        this.gjjInterestSum = gjjInterestSum;
    }

    public String getAvgDiff() {
        return avgDiff;
    }

    public void setAvgDiff(String avgDiff) {
        this.avgDiff = avgDiff;
    }

    public String getSumDiff() {
        return sumDiff;
    }

    public void setSumDiff(String sumDiff) {
        this.sumDiff = sumDiff;
    }

    public String getGjjRepayMonth() {
        return gjjRepayMonth;
    }

    public void setGjjRepayMonth(String gjjRepayMonth) {
        this.gjjRepayMonth = gjjRepayMonth;
    }

    public String getSyRepayMonth() {
        return syRepayMonth;
    }

    public void setSyRepayMonth(String syRepayMonth) {
        this.syRepayMonth = syRepayMonth;
    }

    public String getSyLoanInterestSum() {
        return syLoanInterestSum;
    }

    public void setSyLoanInterestSum(String syLoanInterestSum) {
        this.syLoanInterestSum = syLoanInterestSum;
    }

    public String getGjjLoanInterestSum() {
        return gjjLoanInterestSum;
    }

    public void setGjjLoanInterestSum(String gjjLoanInterestSum) {
        this.gjjLoanInterestSum = gjjLoanInterestSum;
    }

    public String getSyPrincipalInterestSum() {
        return syPrincipalInterestSum;
    }

    public void setSyPrincipalInterestSum(String syPrincipalInterestSum) {
        this.syPrincipalInterestSum = syPrincipalInterestSum;
    }

    public String getGjjPrincipalInterestSum() {
        return gjjPrincipalInterestSum;
    }

    public void setGjjPrincipalInterestSum(String gjjPrincipalInterestSum) {
        this.gjjPrincipalInterestSum = gjjPrincipalInterestSum;
    }

    public String getRepaySumMonth() {
        return repaySumMonth;
    }

    public void setRepaySumMonth(String repaySumMonth) {
        this.repaySumMonth = repaySumMonth;
    }

    public String getIntersetSum() {
        return IntersetSum;
    }

    public void setIntersetSum(String intersetSum) {
        IntersetSum = intersetSum;
    }

    public String getPrincipalInterestSum() {
        return principalInterestSum;
    }

    public void setPrincipalInterestSum(String principalInterestSum) {
        this.principalInterestSum = principalInterestSum;
    }

    public String getMaxYear() {
        return maxYear;
    }

    public void setMaxYear(String maxYear) {
        this.maxYear = maxYear;
    }

    public String getGjjLoan() {
        return gjjLoan;
    }

    public void setGjjLoan(String gjjLoan) {
        this.gjjLoan = gjjLoan;
    }

    public String getSyLoan() {
        return syLoan;
    }

    public void setSyLoan(String syLoan) {
        this.syLoan = syLoan;
    }
}
