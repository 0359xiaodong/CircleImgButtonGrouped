package com.edp.circlebuttongrouped;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @author ESS
 * Extensão de ImageView que desenha imagem redonda
 */
public class CircleImgBtn extends ImageView{
	
	private int width;
	private int height;
	private int borderWidth;
	private Paint paint;
	private Paint paintBorder;
	private int defaultColor;

	public CircleImgBtn(Context context) {
		this(context, null);
	}

	@SuppressLint("Recycle")
	public CircleImgBtn(Context context, AttributeSet attrs) {
		super(context, attrs);

		paint = new Paint();
		paint.setAntiAlias(true);

		paintBorder = new Paint();
		paintBorder.setAntiAlias(true);

		TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CircleImgBtn);
		setHeight(attributes.getInt(R.styleable.CircleImgBtn_height, 150));
		setWidth(attributes.getInt(R.styleable.CircleImgBtn_width, 150));
		
		if(attributes.getBoolean(R.styleable.CircleImgBtn_border, true)) {
			setBorderWidth(attributes.getColor(R.styleable.CircleImgBtn_border_width, 4));
			defaultColor = attributes.getInt(R.styleable.CircleImgBtn_border_color, Color.WHITE);
			setBorderColor(defaultColor);
		}
		
		if(attributes.getBoolean(R.styleable.CircleImgBtn_shadow, false))
			addShadow();
	}

	public int getImageResource() {
		if(getTag() instanceof Integer)
			return (Integer)getTag();
		else
			return 0;
	}

	public int getViewWidth() {
		return width;
	}

	public void setWidth(int viewWidth) {
		this.width = viewWidth;
		this.invalidate();
	}

	public int getViewHeight() {
		return height;
	}

	public void setHeight(int viewHeight) {
		this.height = viewHeight;
		this.invalidate();
	}

	public void setBorderWidth(int borderWidth) {
		this.borderWidth = borderWidth;
		this.invalidate();
	}

	public void setBorderColor(int borderColor) {
		if (paintBorder != null)
			paintBorder.setColor(borderColor);
		this.invalidate();
	}

	public void resetBorderColor(){
		setBorderColor(defaultColor);
	}
	
	public void addShadow() {
		setLayerType(LAYER_TYPE_SOFTWARE, paintBorder);
		paintBorder.setShadowLayer(4.0f, 0.0f, 2.0f, Color.BLACK);
		invalidate();
	}

	public void remShadow() {
		setLayerType(LAYER_TYPE_SOFTWARE, paintBorder);
		paintBorder.clearShadowLayer();
		invalidate();
	}

	@SuppressLint("DrawAllocation")
	@Override
	public void onDraw(Canvas canvas) {
		Bitmap image = null;
		BitmapDrawable bitmapDrawable = (BitmapDrawable) this.getDrawable();
		if (bitmapDrawable != null)
			image = bitmapDrawable.getBitmap();

		if (image != null) {
			BitmapShader shader = new BitmapShader(Bitmap.createScaledBitmap(image, canvas.getWidth(), 
					canvas.getHeight(), true), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
			paint.setShader(shader);
			int circleCenter = width / 2;

			canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth, circleCenter + borderWidth - 4.0f, paintBorder);
			canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth, circleCenter - 4.0f, paint);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int viewWidth = width + (borderWidth * 2);
		int viewHeight = height + (borderWidth * 2) + 2;
		setMeasuredDimension(viewWidth, viewHeight);
	}

}