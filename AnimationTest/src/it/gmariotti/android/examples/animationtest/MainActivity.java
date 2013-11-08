/*******************************************************************************
 * Copyright (c) 2013 Gabriele Mariotti.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package it.gmariotti.android.examples.animationtest;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	LinearLayout mLinearLayout;
	LinearLayout mLinearLayoutHeader;
	ValueAnimator mAnimator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mLinearLayout = (LinearLayout) findViewById(R.id.expandable);
		mLinearLayoutHeader = (LinearLayout) findViewById(R.id.header);
		/** Use addAnim(LayoutListener, LayoutToBeExpanded) **/
		addAnim(mLinearLayout, mLinearLayoutHeader);
		
	}
	
    public void addAnim(final View layout, final View exp)
	
    layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (exp.getVisibility() == View.GONE) {
                    expand(exp);
                } else {
                    collapse(exp);
                }
            }
        });
		
    }
	
    private void expand(View exp) {
        //set Visible
        summary.setVisibility(View.VISIBLE);

                final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                exp.measure(widthSpec, heightSpec);

                mAnimator = slideAnimator(0, exp.getMeasuredHeight(), summary);

        mAnimator.start();
    }

    private void collapse(final View exp) {
        int finalHeight = exp.getHeight();

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0, summary);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                //Height=0, but it set visibility to GONE
                exp.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        mAnimator.start();
    }


    private ValueAnimator slideAnimator(int start, int end, final View exp) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);


        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();

                ViewGroup.LayoutParams layoutParams = exp.getLayoutParams();
                layoutParams.height = value;
                exp.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

}
