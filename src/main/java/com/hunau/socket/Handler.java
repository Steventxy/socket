package com.hunau.socket;

import com.hunau.domain.Data;
import com.hunau.service.Impl.DataServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author TanXY
 * @create 2020/6/21 - 10:46
 */

@Component
public class Handler extends ChannelInboundHandlerAdapter {

    @Autowired
    private DataServiceImpl dataService;
    private static Handler handler;

    double tempAvg = 0;
    double humiAvg = 0;

    private List<Data> list = new ArrayList<>(12);

    @PostConstruct
    private void init() {
        handler = this;
        handler.dataService = this.dataService;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);

        String str = (String) msg;

        System.out.println(msg);

        //如果数据校验正确，就将其存入数据库
        if (!str.equals("error")) {

            String[] formatData = str.split(",");

            disposeData(formatData);
//            handler.dataService.collData(
//                    new Data(Double.parseDouble(formatData[0]), Double.parseDouble(formatData[1])));
        }

    }


    /**
     * 对采集到的数据进行分析和处理
     *
     * @param formatData
     * @return void
     * @author TanXY
     * @date 2020/6/21 15:38
     */
    private void disposeData(String[] formatData) {
        Data data = new Data();

        data.setTemperature(Double.parseDouble(formatData[0]));
        data.setHumidity(Double.parseDouble(formatData[1]));


        //如果采集的数据积累了十个，就将list清空，取这30秒的平均值存入数据库
        if (list.size() >= 10) {
            list.clear();
            System.out.println("数据达到10个\n" + new Data(tempAvg, humiAvg));
            handler.dataService.collData(new Data(tempAvg, humiAvg));
            tempAvg = 0;
            humiAvg = 0;
        }

        //如果该次采集的数据和之前采集数据的均值相差1，就清空前面收集的数据，并将前面数据的均值存入数据库
        if (tempAvg - data.getTemperature() > 1 || humiAvg - data.getHumidity() >= 1) {
            list.clear();
            System.out.println("数据出现变化\n" + new Data(tempAvg, humiAvg));
            handler.dataService.collData(new Data(tempAvg, humiAvg));
            tempAvg = 0;
            humiAvg = 0;
        }

        list.add(data);

        Iterator iterator = list.iterator();
        //遍历list，计算平均值
        tempAvg += data.getTemperature();
        humiAvg += data.getHumidity();
//        while(iterator.hasNext()){
//            Data tempData = (Data)iterator.next();
//            tempAvg += tempData.getTemperature();
//            humiAvg += tempData.getHumidity();
//        }

        if (list.size() >= 2){
            tempAvg /= 2;
            humiAvg /= 2;
        }


        System.out.println(tempAvg);
        System.out.println(humiAvg);
        System.out.println();



//        list.forEach(System.out::println);
    }
}
