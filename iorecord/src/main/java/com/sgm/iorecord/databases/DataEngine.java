package com.sgm.iorecord.databases;

import android.text.TextUtils;

import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.sgm.iorecord.model.IOBean;
import com.sgm.iorecord.model.IOTopBean;
import com.sgm.iorecord.utils.CommandExecution;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * Created by s2s8tb on 2019/9/26.
 */

public class DataEngine {

    public CommandExecution.CommandResult createCommandResult() {
        CommandExecution.CommandResult result = new CommandExecution.CommandResult();
        result.result = 1;
        result.successMsg = "1192,32,22,34,64,com.sgm.iorecord;1193,32,22,34,64,com.sgm.rwutils;1192,32,22,34,64,com.sgm.calendar";
        return result;
    }

    private static class DataEngineHolder {
        static final DataEngine instance = new DataEngine();
    }

    public static DataEngine gerInstance() {
        return DataEngineHolder.instance;
    }

    /**
     * for test
     *
     * @return IOBean
     */
    public IOBean createIOBean() {
        IOBean ioBean = new IOBean();
        ioBean.setWchar("1231231");
        ioBean.setRchar("54545");
        ioBean.setWrite_bytes("455354545");
        ioBean.setDate(new Date());
        return ioBean;
    }

    public IOTopBean createIOTopBean() {
        String[] pros = new String[]{"com.sgm.iorecord", "com.sgm.rwutils", "com.sgm.calendar"};
        IOTopBean ioBean = new IOTopBean();
        ioBean.setPID(String.valueOf(new Random().nextInt(1000) + 1));
        ioBean.setREAD(String.valueOf(new Random().nextInt(10000000)));
        ioBean.setREAD_SPEED(String.valueOf(new Random().nextFloat() * 100));
        ioBean.setWRITTEN(String.valueOf(new Random().nextInt(2000)));
        ioBean.setWRITE_SPEED(String.valueOf(new Random().nextFloat() * 10));
        ioBean.setPROCESS(pros[new Random().nextInt(2)]);
        ioBean.setDate(new Date());
        return ioBean;
    }

    public List<IOTopBean> convertToIOBeanListFromResult(@Nullable CommandExecution.CommandResult result) {
        List<IOTopBean> ioBeans = new ArrayList<>();
        if (result != null && !TextUtils.isEmpty(result.successMsg)) {
            String[] lines = result.successMsg.split(";");
            IOTopBean ioBean;
            for (String line : lines) {
                String[] processLine = line.split(",");
                ioBean = new IOTopBean(processLine[0], processLine[1], processLine[2], processLine[3],
                        processLine[4], processLine[5], new Date());
                ioBeans.add(ioBean);
            }
        }
        return ioBeans;
    }

    /**
     * 将数据转换为图表识别的数据
     *
     * @return
     */
    public PieDataSet convertToPieDataFromTopList(List<PieEntry> pieEntries) {
        PieDataSet dataSet = new PieDataSet(pieEntries, "Results Legends");
        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        return dataSet;
    }

    /**
     * 将从数据库查询到的数据，按照包名求和
     * 为了不漏掉应用被杀前的数据
     *
     * @param ioTopBeans
     * @return
     */
    private Map<String, List<IOTopBean>> filterByPackage(List<IOTopBean> ioTopBeans) {
        List<IOTopBean> topBeans;
        Map<String, List<IOTopBean>> topBeanMap = new HashMap<>();
        for (IOTopBean top : ioTopBeans) {
            if (topBeanMap.get(top.getPROCESS()) == null) {
                topBeans = new ArrayList<>();
                topBeans.add(top);
                topBeanMap.put(top.getPROCESS(), topBeans);
            } else {
                topBeanMap.get(top.getPROCESS()).add(top);
            }
        }
        return topBeanMap;
    }

}
