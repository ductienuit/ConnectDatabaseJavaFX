/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlicongviecfx.modal;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author DucTien
 */
public class CongViec implements Serializable {

    public CongViec(int macongviec, String tencongviec, String thoigianbatdau, String thoigianketthuc, int douutien, int khoiluonghoanthanh, String ghichu) {
        this.macongviec = macongviec;
        this.tencongviec = tencongviec;
        this.thoigianbatdau = thoigianbatdau;
        this.thoigianketthuc = thoigianketthuc;
        this.douutien = douutien;
        this.khoiluonghoanthanh = khoiluonghoanthanh;

        if(khoiluonghoanthanh>=50)
            this.xeploai ="Hoàn thành";
        else
            this.xeploai ="Không hoàn thành";
        
        this.ghichu = ghichu;
    }

    public CongViec() {
    }

    public int getMacongviec() {
        return macongviec;
    }

    public void setMacongviec(int macongviec) {
        this.macongviec = macongviec;
    }

    public String getTencongviec() {
        return tencongviec;
    }

    public void setTencongviec(String tencongviec) {
        this.tencongviec = tencongviec;
    }

    public String getThoigianbatdau() {
        return thoigianbatdau;
    }

    public void setThoigianbatdau(String thoigianbatdau) {
        this.thoigianbatdau = thoigianbatdau;
    }

    public String getThoigianketthuc() {
        return thoigianketthuc;
    }

    public void setThoigianketthuc(String thoigianketthuc) {
        this.thoigianketthuc = thoigianketthuc;
    }

    public int getDouutien() {
        return douutien;
    }

    public void setDouutien(int douutien) {
        this.douutien = douutien;
    }

    public int getKhoiluonghoanthanh() {
        return khoiluonghoanthanh;
    }

    public void setKhoiluonghoanthanh(int khoiluonghoanthanh) {
        this.khoiluonghoanthanh = khoiluonghoanthanh;
        if(khoiluonghoanthanh>=50)
            this.xeploai ="Hoàn thành";
        else
            this.xeploai ="Không hoàn thành";
    }

    public String getXeploai() {
        return xeploai;
    }

    public void setXeploai(String xeploai) {
        this.xeploai = xeploai;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    @Override
    public String toString()
    {
        return macongviec+tencongviec+thoigianbatdau+thoigianketthuc+douutien+khoiluonghoanthanh+xeploai+ghichu;
    }
    private int macongviec;
    private String tencongviec;
    private String thoigianbatdau;
    private String thoigianketthuc;
    private int douutien;
    private int khoiluonghoanthanh;
    private String xeploai;    
    private String muchoanthanh;
    private String ghichu;
}
