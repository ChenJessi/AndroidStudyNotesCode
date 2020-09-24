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
 * UI - �鶯�Ľ�����ϰ
 */
class FishDrawable constructor() : Drawable(){
    // ͸����
    private val OTHER_ALPHA = 110
    /**
     * �� �������еĳߴ綼������ͷ�뾶Ϊ��λ���Ƶ�
     */
    //��ͷ�뾶
    private var HEAD_RADIUS = 100f
    // ������
    private var BODY_LENGTH = HEAD_RADIUS * 3.2f;
    // Ѱ��������ʼ��������߳�
    private var FIND_FINS_LENGTH = 0.9f * HEAD_RADIUS;
    // �����ĳ���
    private var FINS_LENGTH = 1.3f * HEAD_RADIUS;
    // ��Բ�İ뾶
    private var BIG_CIRCLE_RADIUS = 0.7f * HEAD_RADIUS;
    // ��Բ�İ뾶
    private var MIDDLE_CIRCLE_RADIUS = 0.6f * BIG_CIRCLE_RADIUS;
    // СԲ�뾶
    private var SMALL_CIRCLE_RADIUS = 0.4f * MIDDLE_CIRCLE_RADIUS;
    // --Ѱ��β����ԲԲ�ĵ��߳�
    private final var FIND_MIDDLE_CIRCLE_LENGTH = BIG_CIRCLE_RADIUS * (0.6f + 1);
    // --Ѱ��β��СԲԲ�ĵ��߳�
    private final var FIND_SMALL_CIRCLE_LENGTH = MIDDLE_CIRCLE_RADIUS * (0.4f + 2.7f);
    // --Ѱ�Ҵ������εױ����ĵ���߳�
    private final var FIND_TRIANGLE_LENGTH = MIDDLE_CIRCLE_RADIUS * 2.7f;


    /**
     *  ������ĵ� ���еĲ����Ͷ������������ĵ�Ϊ��׼
     */
    val middlePoint by lazy{
        PointF(4.19f * HEAD_RADIUS, 4.19f * HEAD_RADIUS)
    }
    /**
     * ��ͷԲ�ĵ�����
     */

    val headPoint by lazy { PointF() }
    /**
     * ������ת�ĽǶ�
     */
    private var fishMainAngle = 0f
    private val mPaint by lazy {
        Paint().apply {
            // �����
            isAntiAlias = true
            // �������
            style = Paint.Style.FILL
            // ����
            isDither = true
            // ������ɫ
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

        // ��ȡ��ͷ��Բ��
        headPoint.set(calculatePoint(middlePoint, BODY_LENGTH / 2, fishAngle))
        canvas.drawCircle(headPoint.x, headPoint.y, HEAD_RADIUS, mPaint)

        // ��������d  ������ʼ��
        val rightFinsPoint = calculatePoint(headPoint, FIND_FINS_LENGTH, fishAngle - 110)
        makeFins(canvas, rightFinsPoint,fishAngle, true)

        // ��������
        val leftFinsPoint = calculatePoint(headPoint, FIND_FINS_LENGTH, fishAngle + 110)
        makeFins(canvas, leftFinsPoint,fishAngle, false)
        //��֫1 ��Բ
        // ��Բ��Բ�ļ�Ϊ����ײ�������
        val bodyBottomCenterPoint = calculatePoint(middlePoint,  BODY_LENGTH / 2, fishAngle - 180)
        canvas.drawCircle(bodyBottomCenterPoint.x, bodyBottomCenterPoint.y, BIG_CIRCLE_RADIUS, mPaint)

        // ��֫1 �Ķ���
        val segmentAngle = fishMainAngle + cos(Math.toRadians(currentValue * 1.5 * frequence)).toFloat() * 15f
        val segment2Angle = fishMainAngle + sin(Math.toRadians(currentValue * 1.5 * frequence)).toFloat() * 35f
        // ��֫1
        val middleCenterPoint = makeSegment(canvas, bodyBottomCenterPoint, BIG_CIRCLE_RADIUS, MIDDLE_CIRCLE_RADIUS, FIND_MIDDLE_CIRCLE_LENGTH, segmentAngle)
        // ��֫2
        makeSegment(canvas, middleCenterPoint, MIDDLE_CIRCLE_RADIUS, SMALL_CIRCLE_RADIUS, FIND_SMALL_CIRCLE_LENGTH, segment2Angle)

        // ��β��������
        //  ��������

        val findEdgeLength = abs(sin(Math.toRadians(currentValue * 1.5 * frequence)).toFloat() * BIG_CIRCLE_RADIUS)
        val triangleAngle = fishMainAngle + sin(Math.toRadians(currentValue * 1.5 * frequence)).toFloat() * 35f
        makeTriangle(canvas, middleCenterPoint, FIND_TRIANGLE_LENGTH, findEdgeLength, triangleAngle)
        makeTriangle(canvas, middleCenterPoint, FIND_TRIANGLE_LENGTH - 20, findEdgeLength - 30, triangleAngle)
        // ����
        makeBody(canvas, headPoint, bodyBottomCenterPoint, fishAngle)
    }

    /**
     * ��������
     */
    private fun makeBody(canvas: Canvas , headPoint : PointF, bodyBottomCenterPoint : PointF , fishAngle: Float){
        // ������ĸ���
        val bottomLeftPoint = calculatePoint(bodyBottomCenterPoint, BIG_CIRCLE_RADIUS, fishAngle + 90)
        val bottomRightPoint = calculatePoint(bodyBottomCenterPoint, BIG_CIRCLE_RADIUS, fishAngle - 90)
        val topLeftPoint = calculatePoint(headPoint, HEAD_RADIUS, fishAngle + 90)
        val topRightPoint = calculatePoint(headPoint, HEAD_RADIUS, fishAngle - 90)
        // ���Ƶ�
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
     * β��������
     * @param vertexPoint   �����ζ���
     * @param findLength  ���㵽�ױߵĴ�ֱ����
     * @param sideLength  �߳�
     */
    private fun makeTriangle(canvas: Canvas, vertexPoint : PointF , findLength: Float, sideLength : Float, fishAngle : Float){
        // �����εױߵ���������
        val bottomCenterPoint = calculatePoint(vertexPoint, findLength, fishAngle - 180)
        // �����εױ�������
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
     * ��֫
     * @param bottomCenterPoint �����µ����ĵ�
     * @param bigRadius ��Բ�뾶
     * @param smallRadius СԲ�뾶
     * @param findLength ��Բ���ĵ㵽СԲ���ĵ�ľ���  �������ϵ׵��µ׵Ĵ�ֱ����
     */
    private fun makeSegment(canvas: Canvas, bottomCenterPoint: PointF, bigRadius : Float,  smallRadius : Float , findLength : Float , fishAngle: Float ) : PointF {

        // ��֫ ��Բ
        val middleCenterPoint = calculatePoint(bottomCenterPoint,  findLength, fishAngle - 180)
        canvas.drawCircle(middleCenterPoint.x, middleCenterPoint.y, smallRadius, mPaint)

        val bottomLeftPoint = calculatePoint(bottomCenterPoint, bigRadius, fishAngle + 90)
        val bottomRightPoint = calculatePoint(bottomCenterPoint, bigRadius, fishAngle - 90)
        val upperBottomLeftPoint = calculatePoint(middleCenterPoint, smallRadius, fishAngle + 90)
        val upperBottomRightPoint = calculatePoint(middleCenterPoint, smallRadius, fishAngle - 90)

        // �������ѽ�֫
        mPath.reset()
        mPath.moveTo(bottomLeftPoint.x, bottomLeftPoint.y)
        mPath.lineTo(bottomRightPoint.x, bottomRightPoint.y)
        mPath.lineTo(upperBottomRightPoint.x, upperBottomRightPoint.y)
        mPath.lineTo(upperBottomLeftPoint.x, upperBottomLeftPoint.y)
        canvas.drawPath(mPath, mPaint)
        return middleCenterPoint
    }


    /**
     * С��Ķ���
     */
    private fun initAnimator(){
        val animator = ValueAnimator.ofFloat(0f, 3600f).apply {
            startDelay = 1000
            // ��������
            duration = 3000
            // ����ѭ��
            repeatCount = ValueAnimator.INFINITE
            // �ظ�ģʽ
            repeatMode = ValueAnimator.RESTART
            interpolator = LinearInterpolator()
            start()
        }.addUpdateListener {
            currentValue = it.animatedValue as Float
            invalidateSelf()
        }

    }







    /**
     * ����͸����
     */
    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    /**
     * ���ֵ�� ���Ը��� setAlpha ��ֵ���е���
     * ���磺 alpha == 0 ʱ���� PixelFormat.TRANSLUCENT  alpha == 255 ʱ����Ϊ PixelFormat.OPAQUE
     * PixelFormat.OPAQUE  ��ȫ��͸�����ڸ������������������
     * PixelFormat.TRANSPARENT   ͸������ȫ����ʾ�κζ���
     * PixelFormat.TRANSLUCENT �� ��͸�� ֻ�л��Ƶĵط����ڸǵ�������
     */
    override fun getOpacity(): Int {
        return  PixelFormat.TRANSLUCENT
    }


    /**
     *  ��ɫ������
     *  �ڻ��Ƴ���֮ǰ�����������ݵ�ÿһ�����ض��ᱻ��ɫ�������ı�
     */
    override fun setColorFilter(colorFilter: ColorFilter?) {
        //һ�㽫��ֱֵ�ӽ��� paint ����
        mPaint.colorFilter = colorFilter
    }

    override fun getIntrinsicWidth(): Int {
        return (8.38f * HEAD_RADIUS).toInt()
    }

    override fun getIntrinsicHeight(): Int {
        return (8.38f * HEAD_RADIUS).toInt()
    }


    /**
     * @param calculatePoint ��ʼ������
     * @param length  ��ʼ�㵽����ĵ��ֱ�߾���
     * @param angle ֱ����x��ļн� ע�⣺Ϊ android����ϵ�н�
     *
     * �������Ǻ�������λ��
     *  sinA = a / c
     *  cosA = b / c
     */
    private fun calculatePoint(startPointF: PointF, length : Float, angle : Float) : PointF{
        //  x����
        val deltaX =   cos(Math.toRadians(angle.toDouble())) * length
        // y����
        val deltaY = sin(Math.toRadians((angle - 180).toDouble())) * length
        return PointF((startPointF.x + deltaX).toFloat(), (startPointF.y + deltaY).toFloat())
    }


    /**
     * ��������
     */
    private fun makeFins(canvas: Canvas , startPointF: PointF, fishAngle : Float, isRight : Boolean){
        //�������Ƶ�����������᷽��ĽǶ�
        val controlAngle = 115

        // �����Ľ�����
        val endPoint = calculatePoint(startPointF, FINS_LENGTH, fishAngle - 180)

        // ���� �������Ƶ������
        //  �������Ƶ�ĸ߶�Ϊ  1.8������������  FINS_LENGTH * 1.8
        val controlPoint = calculatePoint(startPointF, FINS_LENGTH * 1.8f, if (isRight) (fishAngle - controlAngle) else (fishAngle + controlAngle))
        // ��������
        mPath.reset()
        mPath.moveTo(startPointF.x, startPointF.y)
        // quadTo ��������
        mPath.quadTo(controlPoint.x, controlPoint.y, endPoint.x, endPoint.y)
        canvas.drawPath(mPath, mPaint)
    }



}