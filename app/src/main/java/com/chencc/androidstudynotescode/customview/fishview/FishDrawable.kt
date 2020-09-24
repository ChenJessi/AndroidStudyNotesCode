package com.chencc.androidstudynotescode.customview.fishview

import android.animation.ValueAnimator
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.animation.LinearInterpolator
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

private const val TAG = "FishDrawable"
/**
 * UI - 灵动的锦鲤练习
 */
class FishDrawable constructor() : Drawable(){
    // 透明度
    private val OTHER_ALPHA = 110
    /**
     * 鱼 身上所有的尺寸都是以鱼头半径为单位绘制的
     */
    //鱼头半径
    private var HEAD_RADIUS = 100f
    // 鱼身长度
    private var BODY_LENGTH = HEAD_RADIUS * 3.2f;
    // 寻找鱼鳍起始点坐标的线长
    private var FIND_FINS_LENGTH = 0.9f * HEAD_RADIUS;
    // 鱼鳍的长度
    private var FINS_LENGTH = 1.3f * HEAD_RADIUS;
    // 大圆的半径
    private var BIG_CIRCLE_RADIUS = 0.7f * HEAD_RADIUS;
    // 中圆的半径
    private var MIDDLE_CIRCLE_RADIUS = 0.6f * BIG_CIRCLE_RADIUS;
    // 小圆半径
    private var SMALL_CIRCLE_RADIUS = 0.4f * MIDDLE_CIRCLE_RADIUS;
    // --寻找尾部中圆圆心的线长
    private final var FIND_MIDDLE_CIRCLE_LENGTH = BIG_CIRCLE_RADIUS * (0.6f + 1);
    // --寻找尾部小圆圆心的线长
    private final var FIND_SMALL_CIRCLE_LENGTH = MIDDLE_CIRCLE_RADIUS * (0.4f + 2.7f);
    // --寻找大三角形底边中心点的线长
    private final var FIND_TRIANGLE_LENGTH = MIDDLE_CIRCLE_RADIUS * 2.7f;


    /**
     *  鱼的重心点 所有的部件和动画都是以重心点为基准
     */
    val middlePoint by lazy{
        PointF(4.19f * HEAD_RADIUS, 4.19f * HEAD_RADIUS)
    }
    /**
     * 鱼头圆心的坐标
     */

    val headPoint by lazy { PointF() }
    /**
     * 鱼身旋转的角度
     */
    private var fishMainAngle = 0f
    private val mPaint by lazy {
        Paint().apply {
            // 抗锯齿
            isAntiAlias = true
            // 填充类型
            style = Paint.Style.FILL
            // 防抖
            isDither = true
            // 设置颜色
            setARGB(OTHER_ALPHA , 244, 92, 71)
        }
    }


    private val mPath by lazy { Path() }

    private var currentValue = 0f
    private var frequence = 1f

    init {
        initAnimator()
    }




    override fun draw(canvas: Canvas) {

        val fishAngle = fishMainAngle + sin(Math.toRadians(currentValue * 1.2 * frequence)).toFloat() * 10f

        // 获取鱼头的圆心
        headPoint.set(calculatePoint(middlePoint, BODY_LENGTH / 2, fishAngle))
        canvas.drawCircle(headPoint.x, headPoint.y, HEAD_RADIUS, mPaint)

        // 画鱼右鳍d  右鳍开始点
        val rightFinsPoint = calculatePoint(headPoint, FIND_FINS_LENGTH, fishAngle - 110)
        makeFins(canvas, rightFinsPoint,fishAngle, true)

        // 画左鱼鳍
        val leftFinsPoint = calculatePoint(headPoint, FIND_FINS_LENGTH, fishAngle + 110)
        makeFins(canvas, leftFinsPoint,fishAngle, false)
        //节肢1 大圆
        // 大圆的圆心即为身体底部的中心
        val bodyBottomCenterPoint = calculatePoint(middlePoint,  BODY_LENGTH / 2, fishAngle - 180)
        canvas.drawCircle(bodyBottomCenterPoint.x, bodyBottomCenterPoint.y, BIG_CIRCLE_RADIUS, mPaint)

        // 节肢1 的动画
        val segmentAngle = fishMainAngle + cos(Math.toRadians(currentValue * 1.5 * frequence)).toFloat() * 15f
        val segment2Angle = fishMainAngle + sin(Math.toRadians(currentValue * 1.5 * frequence)).toFloat() * 35f
        // 节肢1
        val middleCenterPoint = makeSegment(canvas, bodyBottomCenterPoint, BIG_CIRCLE_RADIUS, MIDDLE_CIRCLE_RADIUS, FIND_MIDDLE_CIRCLE_LENGTH, segmentAngle)
        // 节肢2
        makeSegment(canvas, middleCenterPoint, MIDDLE_CIRCLE_RADIUS, SMALL_CIRCLE_RADIUS, FIND_SMALL_CIRCLE_LENGTH, segment2Angle)

        // 画尾部三角形
        //  大三角形

        val findEdgeLength = abs(sin(Math.toRadians(currentValue * 1.5 * frequence)).toFloat() * BIG_CIRCLE_RADIUS)
        val triangleAngle = fishMainAngle + sin(Math.toRadians(currentValue * 1.5 * frequence)).toFloat() * 35f
        makeTriangle(canvas, middleCenterPoint, FIND_TRIANGLE_LENGTH, findEdgeLength, triangleAngle)
        makeTriangle(canvas, middleCenterPoint, FIND_TRIANGLE_LENGTH - 20, findEdgeLength - 30, triangleAngle)
        // 鱼身
        makeBody(canvas, headPoint, bodyBottomCenterPoint, fishAngle)
    }

    /**
     * 绘制鱼身
     */
    private fun makeBody(canvas: Canvas , headPoint : PointF, bodyBottomCenterPoint : PointF , fishAngle: Float){
        // 身体的四个点
        val bottomLeftPoint = calculatePoint(bodyBottomCenterPoint, BIG_CIRCLE_RADIUS, fishAngle + 90)
        val bottomRightPoint = calculatePoint(bodyBottomCenterPoint, BIG_CIRCLE_RADIUS, fishAngle - 90)
        val topLeftPoint = calculatePoint(headPoint, HEAD_RADIUS, fishAngle + 90)
        val topRightPoint = calculatePoint(headPoint, HEAD_RADIUS, fishAngle - 90)
        // 控制点
        val controlLeft = calculatePoint(headPoint, BODY_LENGTH * 0.56f, fishAngle + 130)
        val controlRight = calculatePoint(headPoint, BODY_LENGTH * 0.56f, fishAngle - 130)

        mPath.reset()
        mPath.moveTo(bottomLeftPoint.x, bottomLeftPoint.y)
        mPath.lineTo(bottomRightPoint.x, bottomRightPoint.y)
        mPath.quadTo(controlRight.x, controlRight.y, topRightPoint.x, topRightPoint.y)
        mPath.lineTo(topLeftPoint.x, topLeftPoint.y)
        mPath.quadTo(controlLeft.x, controlLeft.y, bottomLeftPoint.x, bottomLeftPoint.y)
        canvas.drawPath(mPath, mPaint)
    }

    /**
     * 尾部三角形
     * @param vertexPoint   三角形顶点
     * @param findLength  顶点到底边的垂直距离
     * @param sideLength  边长
     */
    private fun makeTriangle(canvas: Canvas, vertexPoint : PointF , findLength: Float, sideLength : Float, fishAngle : Float){
        // 三角形底边的中心坐标
        val bottomCenterPoint = calculatePoint(vertexPoint, findLength, fishAngle - 180)
        // 三角形底边两个点
        val leftBottomPoint = calculatePoint(bottomCenterPoint, sideLength, fishAngle + 90)
        val rightBottomPoint = calculatePoint(bottomCenterPoint, sideLength, fishAngle - 90)
        mPath.reset()
        mPath.moveTo(vertexPoint.x, vertexPoint.y)
        mPath.lineTo(leftBottomPoint.x, leftBottomPoint.y)
        mPath.lineTo(rightBottomPoint.x, rightBottomPoint.y)
        mPath.lineTo(vertexPoint.x, vertexPoint.y)

        canvas.drawPath(mPath, mPaint)
    }

    /**
     * 节肢
     * @param bottomCenterPoint 梯形下底中心点
     * @param bigRadius 大圆半径
     * @param smallRadius 小圆半径
     * @param findLength 大圆中心点到小圆中心点的距离  即梯形上底到下底的垂直距离
     */
    private fun makeSegment(canvas: Canvas, bottomCenterPoint: PointF, bigRadius : Float,  smallRadius : Float , findLength : Float , fishAngle: Float ) : PointF {

        // 节肢 中圆
        val middleCenterPoint = calculatePoint(bottomCenterPoint,  findLength, fishAngle - 180)
        canvas.drawCircle(middleCenterPoint.x, middleCenterPoint.y, smallRadius, mPaint)

        val bottomLeftPoint = calculatePoint(bottomCenterPoint, bigRadius, fishAngle + 90)
        val bottomRightPoint = calculatePoint(bottomCenterPoint, bigRadius, fishAngle - 90)
        val upperBottomLeftPoint = calculatePoint(middleCenterPoint, smallRadius, fishAngle + 90)
        val upperBottomRightPoint = calculatePoint(middleCenterPoint, smallRadius, fishAngle - 90)

        // 绘制提醒节肢
        mPath.reset()
        mPath.moveTo(bottomLeftPoint.x, bottomLeftPoint.y)
        mPath.lineTo(bottomRightPoint.x, bottomRightPoint.y)
        mPath.lineTo(upperBottomRightPoint.x, upperBottomRightPoint.y)
        mPath.lineTo(upperBottomLeftPoint.x, upperBottomLeftPoint.y)
        canvas.drawPath(mPath, mPaint)
        return middleCenterPoint
    }


    /**
     * 小鱼的动画
     */
    private fun initAnimator(){
        val animator = ValueAnimator.ofFloat(0f, 3600f).apply {
            startDelay = 1000
            // 持续三秒
            duration = 3000
            // 无限循环
            repeatCount = ValueAnimator.INFINITE
            // 重复模式
            repeatMode = ValueAnimator.RESTART
            interpolator = LinearInterpolator()
            start()
        }.addUpdateListener {
            currentValue = it.animatedValue as Float
            invalidateSelf()
        }

    }







    /**
     * 设置透明度
     */
    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    /**
     * 这个值， 可以根据 setAlpha 的值进行调整
     * 比如： alpha == 0 时设置 PixelFormat.TRANSLUCENT  alpha == 255 时设置为 PixelFormat.OPAQUE
     * PixelFormat.OPAQUE  完全不透明，遮盖在它下面的所有内容
     * PixelFormat.TRANSPARENT   透明，完全不显示任何东西
     * PixelFormat.TRANSLUCENT ： 半透明 只有绘制的地方才遮盖地下内容
     */
    override fun getOpacity(): Int {
        return  PixelFormat.TRANSLUCENT
    }


    /**
     *  颜色过滤器
     *  在绘制出来之前，被绘制内容的每一个像素都会被颜色过滤器改变
     */
    override fun setColorFilter(colorFilter: ColorFilter?) {
        //一般将此值直接交给 paint 处理
        mPaint.colorFilter = colorFilter
    }

    override fun getIntrinsicWidth(): Int {
        return (8.38f * HEAD_RADIUS).toInt()
    }

    override fun getIntrinsicHeight(): Int {
        return (8.38f * HEAD_RADIUS).toInt()
    }


    /**
     * @param calculatePoint 起始点坐标
     * @param length  起始点到计算的点的直线距离
     * @param angle 直线与x轴的夹角 注意：为 android坐标系夹角
     *
     * 利用三角函数计算位置
     *  sinA = a / c
     *  cosA = b / c
     */
    private fun calculatePoint(startPointF: PointF, length : Float, angle : Float) : PointF{
        //  x坐标
        val deltaX =   cos(Math.toRadians(angle.toDouble())) * length
        // y坐标
        val deltaY = sin(Math.toRadians((angle - 180).toDouble())) * length
        return PointF((startPointF.x + deltaX).toFloat(), (startPointF.y + deltaY).toFloat())
    }


    /**
     * 绘制鱼鳍
     */
    private fun makeFins(canvas: Canvas , startPointF: PointF, fishAngle : Float, isRight : Boolean){
        //鱼鳍控制点相对于鱼主轴方向的角度
        val controlAngle = 115

        // 右鳍的结束点
        val endPoint = calculatePoint(startPointF, FINS_LENGTH, fishAngle - 180)

        // 计算 鱼鳍控制点的坐标
        //  鱼鳍控制点的高度为  1.8倍的鱼鳍长度  FINS_LENGTH * 1.8
        val controlPoint = calculatePoint(startPointF, FINS_LENGTH * 1.8f, if (isRight) (fishAngle - controlAngle) else (fishAngle + controlAngle))
        // 绘制鱼鳍
        mPath.reset()
        mPath.moveTo(startPointF.x, startPointF.y)
        // quadTo 绘制曲线
        mPath.quadTo(controlPoint.x, controlPoint.y, endPoint.x, endPoint.y)
        canvas.drawPath(mPath, mPaint)
    }



}