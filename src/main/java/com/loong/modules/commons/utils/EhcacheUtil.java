package com.loong.modules.commons.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.net.URL;

/**
 * @program: ssm
 * @description:Ehcache
 * @AUTHOR: tlw
 * @create: 2019-06-07 14:46
 */
public class EhcacheUtil {
    private static String path="/ehcache.xml";
    private static URL url;
    private static EhcacheUtil ehcacheUtil;
    private  CacheManager manager;

    private EhcacheUtil(){
        this.url = this.getClass().getResource(path);
        System.out.println(url);
        this.manager=CacheManager.create(url);
    }

    public static EhcacheUtil getInstance(){
        if (ehcacheUtil==null){
            ehcacheUtil=new EhcacheUtil();
        }
        return ehcacheUtil;
    }

    public  void put(String cacheName,Object key,Object value){
        Cache cache = manager.getCache(cacheName);
        Element element=new Element(key,value);
        cache.put(element);
    }

    public  Object get(String cacheName,Object key){
        Cache cache = manager.getCache(cacheName);
        return cache.get(key).getObjectValue();
    }

    public  void remove(String cacheName,Object key,Object value){
        Cache cache = manager.getCache(cacheName);
        cache.remove(key);
    }
}