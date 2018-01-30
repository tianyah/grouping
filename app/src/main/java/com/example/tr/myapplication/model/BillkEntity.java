package com.example.tr.myapplication.model;


import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by Oubowu on 2016/7/27 12:59.
 */
public class BillkEntity {

    private int count;

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public List<BillInfo> January_list;

    public List<BillInfo> december_list;

    public List<BillInfo> november_list;

    public List<BillInfo> october_list;

    public static class BillInfo implements MultiItemEntity {

        public static final int TYPE_HEADER = 1;
        public static final int TYPE_DATA = 2;

        private int itemType;

        public String billHeaderTime;
        public String spending;
        public String income;


        public String id;
        private String type;
        private String mode;
        private String amount;
        private String created_at;
        private String bill_name;

        public String getBill_name() {
            return bill_name;
        }

        public void setBill_name(String bill_name) {
            this.bill_name = bill_name;
        }

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

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public BillInfo() {
        }

        public BillInfo(int itemType) {
            this.itemType = itemType;
        }

        public BillInfo(int itemType, String billHeaderTime,String spending,String income) {
            this(itemType);
            this.billHeaderTime = billHeaderTime;
            this.spending = spending;
            this.income = income;
        }

        @Override
        public int getItemType() {
            return itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }


    }

}
