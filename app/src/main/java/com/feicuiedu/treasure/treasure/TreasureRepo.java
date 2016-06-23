package com.feicuiedu.treasure.treasure;

import com.feicuiedu.treasure.treasure.home.Area;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * 宝藏仓库
 */
public class TreasureRepo {

    private static TreasureRepo sInstance;
    public static TreasureRepo getInstance() {
        if (sInstance == null) {
            sInstance = new TreasureRepo();
        }
        return sInstance;
    }

    private TreasureRepo(){}

    // 用来保存所有宝藏的
    private final HashMap<Integer, Treasure> treasureMap = new HashMap<Integer, Treasure>();
    // 用来保存所有区域的
    private final HashSet<Area> cachedAreas = new HashSet<Area>();

    public void cache(Area area) {
        cachedAreas.add(area);
    }

    public boolean isCached(Area area){
        // 是否已存在 (contains 包含)
        return cachedAreas.contains(area);
    }

    public void addTreasure(List<Treasure> treasureList){
        for(Treasure treasure : treasureList){
            treasureMap.put(treasure.getId(), treasure);
        }
    }

    public Treasure getTreasure(int id){
        return treasureMap.get(id);
    }

    public Collection<Treasure> getTreasures(){
        return treasureMap.values();
    }

    public void clear(){
        cachedAreas.clear();
        treasureMap.clear();
    }




}
