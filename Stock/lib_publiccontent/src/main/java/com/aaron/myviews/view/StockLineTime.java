package com.aaron.myviews.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Shader;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.aaron.myviews.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @类描述：股票分时图
 * @创建人：龙章煌
 * @创建时间：2015-05-01
 * @修改人：
 * @修改时间：
 * @修改备注：
 * @version
 */
public class StockLineTime extends View {

    private List<Integer> milliliter;
    private float tb;
    private float interval_left_right;
    private float interval_left;
    private float margin_bottom;
    private Paint paint_date, paint_brokenLine, paint_dottedline,
            paint_brokenline_big, framPanint;

    private int time_index;
    private Bitmap bitmap_point;
    private Path path;
    private float dotted_text;

    public float getDotted_text() {
        return dotted_text;
    }

    public void setDotted_text(float dotted_text) {
        this.dotted_text = dotted_text;
    }

    private int fineLineColor = 0x5faaaaaa; // 灰色
    private int blueLineColor = 0xff00ffff; // 蓝色
    private int orangeLineColor = 0xffd56f2b; // 橙色

    public StockLineTime(Context context, List<Integer> milliliter) {
        super(context);
        init(milliliter);
    }

    public void init(List<Integer> milliliter) {
        if (null == milliliter || milliliter.size() == 0)
            return;
        this.milliliter = delZero(milliliter);
        Resources res = getResources();
        //tb = res.getDimension(R.dimen.historyscore_tb);
        tb = 10;
        interval_left_right = tb * 5.0f;
        interval_left = tb * 0.5f;
        margin_bottom=8*tb * 0.2f;

        paint_date = new Paint();
        paint_date.setStrokeWidth(tb * 0.1f);
        paint_date.setTextSize(tb * 1.2f);
        paint_date.setColor(fineLineColor);
        
        //折线
        paint_brokenLine = new Paint();
        paint_brokenLine.setFakeBoldText(true);
        paint_brokenLine.setStrokeWidth(tb * 0.1f);
        paint_brokenLine.setColor(getResources().getColor(R.color.stock_line_color));
        paint_brokenLine.setAntiAlias(true);
       
        paint_dottedline = new Paint();
        paint_dottedline.setStyle(Paint.Style.STROKE);
        paint_dottedline.setColor(fineLineColor);
          
        paint_brokenline_big = new Paint();
        paint_brokenline_big.setStrokeWidth(tb * 0.2f);
        paint_brokenline_big.setColor(fineLineColor);
        paint_brokenline_big.setAntiAlias(true);

        framPanint = new Paint();
        framPanint.setAntiAlias(true);
        framPanint.setStrokeWidth(2f);

        path = new Path();
        bitmap_point = BitmapFactory.decodeResource(getResources(),
                R.drawable.stock_line_bg);
        setLayoutParams(new LayoutParams(
                (int) (this.milliliter.size() * interval_left_right),
                LayoutParams.MATCH_PARENT));
    }

    /**
     * 移除左右为零的数字
     *
     * @return
     */
    public List<Integer> delZero(List<Integer> milliliter) {
        List<Integer> list = new ArrayList<Integer>();
        int sta = 0;
        int end = 0;
        for (int i = 0; i < milliliter.size(); i++) {
            if (milliliter.get(i) != 0) {
                sta = i;
                break;
            }
        }
        for (int i = milliliter.size() - 1; i >= 0; i--) {
            if (milliliter.get(i) != 0) {
                end = i;
                break;
            }
        }
        for (int i = 0; i < milliliter.size(); i++) {
            if (i >= sta && i <= end) {
                list.add(milliliter.get(i));
            }
        }
        time_index = sta;
//		dotted_text = ((Collections.max(milliliter) - Collections .min(milliliter)) / 12.0f * 5.0f);
        return list;
    }

    protected void onDraw(Canvas c) {
        if (null == milliliter || milliliter.size() == 0)
            return;
        drawStraightLine(c);
        drawBrokenLine(c);
        drawDate(c);
    }

    /**
     * 绘制竖线
     *
     * @param c
     */
    public void drawStraightLine(Canvas c) {

        int count_line = 0;
        for (int i = 0; i < milliliter.size(); i++) {
            if (i==0) {//绘制Y
                c.drawLine(interval_left_right * i, 0, interval_left_right * i, getHeight() - margin_bottom, paint_date);
                for (int j = 0; j <10; j++) {
                    c.drawText(String.valueOf(10*(j+1)), 0 ,(getHeight()-margin_bottom)/10*(10-(j+1)), paint_date);
                    if (j==5){//绘制虚线
                        paint_dottedline.setColor(orangeLineColor);
                        Path path = new Path();
                        path.moveTo(0, (getHeight()-margin_bottom)/10*(10-(j+1)));
                        path.lineTo(getWidth(), (getHeight()-margin_bottom)/10*(10-(j+1)));
                        PathEffect effects = new DashPathEffect(new float[] { tb * 0.3f,
                                tb * 0.3f, tb * 0.3f, tb * 0.3f }, tb * 0.1f);
                        paint_dottedline.setPathEffect(effects);
                        c.drawPath(path, paint_dottedline);
                    }

                }
                continue;
            }
            //绘制竖线 连续四条为
            paint_dottedline.setColor(fineLineColor);
            if (count_line == 0) {
                c.drawLine(interval_left_right * i, 0, interval_left_right * i, getHeight() - margin_bottom, paint_date);
            }
            if (count_line == 2) {
                c.drawLine(interval_left_right * i, tb * 1.5f, interval_left_right * i, getHeight() - margin_bottom, paint_date);
            }
            if (count_line == 1 || count_line == 3) {
                Path path = new Path();
                path.moveTo(interval_left_right * i, tb * 1.5f);
                path.lineTo(interval_left_right * i, getHeight() - margin_bottom);
                PathEffect effects = new DashPathEffect(new float[] { tb * 0.3f, tb * 0.3f, tb * 0.3f, tb * 0.3f }, tb * 0.1f);
                paint_dottedline.setPathEffect(effects);
                c.drawPath(path, paint_dottedline);
            }
            count_line++;
            if (count_line >= 4) {
                count_line = 0;
            }
        }
        //绘制X
        c.drawLine(0, getHeight() - margin_bottom, getWidth(), getHeight() - margin_bottom, paint_brokenline_big);
    }

    /**
     * 绘制折线
     *
     * @param c
     */
    public void drawBrokenLine(Canvas c) {
        int index = 0;
        float temp_x = 0;
        float temp_y = 0;
//		float base = (getHeight() - tb * 3.0f) / (Collections.max(milliliter) - Collections.min(milliliter));
        float base = (getHeight() - margin_bottom) / 100;

        Shader mShader = new LinearGradient(0, 0, 0, getHeight(), new int[] {
                Color.argb(100, 0, 255, 255), Color.argb(45, 0, 255, 255),
                Color.argb(10, 0, 255, 255) }, null, Shader.TileMode.CLAMP);
        framPanint.setShader(mShader);

        for (int i = 0; i < milliliter.size() - 1; i++) {
            Log.e("i", ""+milliliter.get(i));
            float x1 = interval_left_right * i;
            float y1 =  getHeight() - margin_bottom - (base * milliliter.get(i));
            float Y1 =  milliliter.get(i);
            float x2 = interval_left_right * (i + 1);
            float y2 =  getHeight() - margin_bottom - (base * milliliter.get(i + 1));
            float Y2 =  milliliter.get(i+1);

            if ((int) (base * milliliter.get(i + 1)) == 0 && index == 0) {
                index++;
                temp_x = x1;
                temp_y = y1;
            }
            if ((int) (base * milliliter.get(i + 1)) != 0 && index != 0) {
                index = 0;
                x1 = temp_x;
                y1 = temp_y;
            }

            paint_date.setColor(orangeLineColor);
            if (i==0) c.drawText(String.valueOf(Y1), x1, y1, paint_date);//绘出第一个�?�的大小

            if (index == 0) {
                c.drawText(String.valueOf(Y2), x2, y2, paint_date);//绘出第i+1个�?�的大小
                c.drawLine(x1, y1, x2, y2, paint_brokenLine);
                path.lineTo(x1, y1);
                if (i != 0)
                    c.drawBitmap(bitmap_point,
                            x1 - bitmap_point.getWidth() / 2,
                            y1 - bitmap_point.getHeight() / 2, null);
                if (i == milliliter.size() - 2) {
                    path.lineTo(x2, y2);
                    path.lineTo(x2, getHeight());
                    path.lineTo(0, getHeight());
                    path.close();
                    c.drawPath(path, framPanint);
                    c.drawBitmap(bitmap_point,
                            x2 - bitmap_point.getWidth() / 2,
                            y2 - bitmap_point.getHeight() / 2, null);
                }
            }
        }
        paint_date.setColor(fineLineColor);

    }

    /**
     * 绘制时间
     *
     * @param c
     */
    public void drawDate(Canvas c) {

        Date date=new Date();//取时
        Calendar calendar =new  GregorianCalendar();
        calendar.setTime(date);
        SimpleDateFormat dateFormat=new SimpleDateFormat("MM-dd");
        String[] dates=new String[milliliter.size()];
        for (int i = 0; i < milliliter.size(); i++) {
            calendar.add(calendar.DATE,1);//把日期往后增加一�?.整数�?后推,负数�?前移�?
            date=calendar.getTime();   //这个时间就是日期�?后推�?天的结果
            dates[i]=dateFormat.format(date).toString();
        }

        for (int i = 0; i < milliliter.size(); i += 1) {
            paint_date.setStrokeWidth(tb * 2.8f);
            c.drawText(dates[i], interval_left_right * i ,getHeight(), paint_date);
        }

    }
}