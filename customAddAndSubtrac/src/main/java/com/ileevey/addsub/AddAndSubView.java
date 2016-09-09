package com.ileevey.addsub;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddAndSubView extends LinearLayout {

	/** 显示文本 */
	private TextView mTextView;
	/** 增加按钮 */
	private Button btAdd;
	/** 减少按钮 */
	private Button btReduce;
	/** 显示文本的长宽 */
	private int textFrameWidth;
	/** 显示文本及button中文字的颜色 */
	private int textColor;
	/** 初始值 */
	private int initValue = 1;
	/** 最大值 */
	private int maxValue;
	/** 最小值 */
	private int minValue;
	/** 显示文本及button中文字的大小 */
	private int textSize;
	/** 显示文本的背景 */
	private Drawable textFrameBackground;
	/** 增加按钮的背景 */
	private Drawable addBackground;
	/** 减少按钮的背景 */
	private Drawable subBackground;
	/** 增加按钮的大小 */
	private int addWidth;
	/** 减少按钮的大小 */
	private int subWidth;
	/** 增加按钮中的文本 */
	private String addText;
	/** 减少按钮中的文本 */
	private String subText;
	private Context context;
	private TextValueChangeListener textValueChangeListener;

	public AddAndSubView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initWidget(context);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AddAndSubView);
		textColor = a.getColor(R.styleable.AddAndSubView_textColor, getResources().getColor(android.R.color.black));
//		textSize = a.getDimensionPixelSize(R.styleable.AddAndSubView_textSize, 16);
		textFrameBackground = a.getDrawable(R.styleable.AddAndSubView_textFrameBackground);
		textFrameWidth = a.getDimensionPixelSize(R.styleable.AddAndSubView_textFrameWidth, 48);
		addBackground = a.getDrawable(R.styleable.AddAndSubView_addBackground);
		subBackground = a.getDrawable(R.styleable.AddAndSubView_subBackground);
		initValue = a.getInt(R.styleable.AddAndSubView_initValue, 0);
		maxValue = a.getInt(R.styleable.AddAndSubView_maxValue, 999);
		minValue = a.getInt(R.styleable.AddAndSubView_minValue, 1);
		addWidth = a.getDimensionPixelSize(R.styleable.AddAndSubView_addWidth, 48);
		subWidth = a.getDimensionPixelSize(R.styleable.AddAndSubView_subWidth, 48);
		addText = a.getString(R.styleable.AddAndSubView_addText);
		subText = a.getString(R.styleable.AddAndSubView_subText);
		if (addBackground!=null) {
			setAddBackground(addBackground);
		}
		setAddText(addText);
		setAddWidth(addWidth);
		setInitValue(initValue);

		setMaxValue(maxValue);
		setMinValue(minValue);
		if (subBackground!=null) {
			setSubBackground(subBackground);
		}
		setSubText(subText);
		setSubWidth(subWidth);
		setTextColor(textColor);
		if (textFrameBackground!=null) {
			setTextFrameBackground(textFrameBackground);
		}
		setTextFrameWidth(textFrameWidth);
		setTextSize(16);
		a.recycle();
	}

	protected void onFinishInflate() {
		super.onFinishInflate();
		addListener();

	}

	public void initWidget(final Context context) {
		LayoutInflater.from(context).inflate(R.layout.add_sub_view, this);
		mTextView = (TextView) findViewById(R.id.et01);
		btAdd = (Button) findViewById(R.id.bt01);
		btReduce = (Button) findViewById(R.id.bt02);

	}

	public void addListener() {
		btAdd.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (!TextUtils.isEmpty(mTextView.getText().toString())) {
					int num = Integer.valueOf(mTextView.getText().toString());
					num++;
					if (num >= maxValue + 1)
						return;
					mTextView.setText(Integer.toString(num));
				}
			}
		});

		btReduce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!TextUtils.isEmpty(mTextView.getText().toString())) {
					int num = Integer.valueOf(mTextView.getText().toString());
					num--;
					if (num <= minValue - 1)
						return;
					mTextView.setText(Integer.toString(num));
				}
			}
		});

		mTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Builder builder = new AlertDialog.Builder(context);
				// LayoutInflater是用来找layout文件夹下的xml布局文件，并且实例化
				LayoutInflater factory = LayoutInflater.from(context);
				// 把activity_login中的控件定义在View中
				View view = factory.inflate(R.layout.dialog_main, null);
				// 将LoginActivity中的控件显示在对话框中
				final EditText edt_num = (EditText) view.findViewById(R.id.edt_num);

				edt_num.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) {

					}

					@Override
					public void beforeTextChanged(CharSequence s, int start, int count, int after) {
						// TODO Auto-generated method stub

					}

					@Override
					public void afterTextChanged(Editable s) {

					}
				});

				edt_num.setText(mTextView.getText().toString());
				builder.setView(view);
				// 设置dialog是否为模态，false表示模态，true表示非模态
				// ab.setCancelable(false);
				// 对话框的创建、显示,这里显示的位置是在屏幕的最下面，但是很不推荐这个种做法，因为距底部有一段空隙
				final AlertDialog dialog = builder.create();
				Window window = dialog.getWindow();
				window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置

				dialog.setOnShowListener(new OnShowListener() {

					@Override
					public void onShow(DialogInterface dialog) {
						edt_num.selectAll();
						// 调用系统输入法
						InputMethodManager inputManager = (InputMethodManager) edt_num.getContext()
								.getSystemService(Context.INPUT_METHOD_SERVICE);
						inputManager.showSoftInput(edt_num, 0);
					}
				});
				dialog.show();

				Button btn = (Button) view.findViewById(R.id.btn_submit);
				btn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						if (edt_num.getText().toString().trim().equals("")) {
							return;
						}
						int num = Integer.parseInt(edt_num.getText().toString());
						if (num > maxValue || num < minValue) {
							Toast.makeText(context, "超出预设范围", Toast.LENGTH_SHORT).show();
							return;
						}
						mTextView.setText(edt_num.getText().toString());
						dialog.dismiss();
					}
				});
			}
		});

		mTextView.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (textValueChangeListener != null) {
					textValueChangeListener.onValueChange(mTextView, Integer.parseInt(s.toString()));
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
	}

	public int getTextFrameWidth() {
		return textFrameWidth;
	}

	public void setTextFrameWidth(int textFrameWidth) {
		this.textFrameWidth = textFrameWidth;
		mTextView.setWidth(textFrameWidth);
		mTextView.setHeight(textFrameWidth);
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
		mTextView.setTextColor(textColor);
		btAdd.setTextColor(textColor);
		btReduce.setTextColor(textColor);
	}

	public int getInitValue() {
		return initValue;
	}

	public void setInitValue(int initValue) {
		this.initValue = initValue;
		mTextView.setText(String.valueOf(initValue));
	}

	public void setTextValue(int value) {
		mTextView.setText(String.valueOf(value));
	}

	public int getTextValue() {
		int num = Integer.valueOf(mTextView.getText().toString());
		return num;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	public int getTextSize() {
		return textSize;
	}

	public void setTextSize(int textSize) {
		this.textSize = textSize;
		mTextView.setTextSize(textSize);
	}

	public Drawable getTextFrameBackground() {
		return textFrameBackground;
	}

	public void setTextFrameBackground(Drawable textFrameBackground) {
		this.textFrameBackground = textFrameBackground;
		mTextView.setBackgroundDrawable(textFrameBackground);
	}

	public Drawable getAddBackground() {
		return addBackground;
	}

	public void setAddBackground(Drawable addBackground) {
		this.addBackground = addBackground;
		Resources res = getResources();
		int color = res.getColor(android.R.color.darker_gray);
		Drawable drawable = new ColorDrawable(color);
		btAdd.setBackgroundDrawable(addBackground == null ? drawable : addBackground);
	}

	public Drawable getSubBackground() {
		return subBackground;
	}

	public void setSubBackground(Drawable subBackground) {
		this.subBackground = subBackground;
		Resources res = getResources();
		int color = res.getColor(android.R.color.darker_gray);
		Drawable drawable = new ColorDrawable(color);
		btReduce.setBackgroundDrawable(subBackground == null ? drawable : subBackground);
	}

	public int getAddWidth() {
		return addWidth;
	}

	public void setAddWidth(int addWidth) {
		this.addWidth = addWidth;
		btAdd.setWidth(addWidth);
		btAdd.setHeight(addWidth);
	}

	public int getSubWidth() {
		return subWidth;
	}

	public void setSubWidth(int subWidth) {
		this.subWidth = subWidth;
		btReduce.setWidth(subWidth);
		btReduce.setHeight(subWidth);
	}

	public String getAddText() {
		return addText;
	}

	public void setAddText(String addText) {
		this.addText = addText;
		btAdd.setText(addText);
	}

	public String getSubText() {
		return subText;
	}

	public void setSubText(String subText) {
		this.subText = subText;
		btReduce.setText(subText);
	}

	public TextValueChangeListener getTextValueChangeListener() {
		return textValueChangeListener;
	}

	public void setTextValueChangeListener(TextValueChangeListener textValueChangeListener) {
		this.textValueChangeListener = textValueChangeListener;
	}
}