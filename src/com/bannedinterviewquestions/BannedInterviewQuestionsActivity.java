package com.bannedinterviewquestions;

import java.util.Random;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class BannedInterviewQuestionsActivity extends Activity {

	private static final String LIST_TAB = "list_tab";
	private static final String RANDOM_TAB = "randon_tab";
	private ViewFlipper listFlipper;
	private ListView questionsList;
	private ListView areasList;
	private TabHost tabHost;
	private TextView randomQuestion;
	private String[] areas;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		initTabsHost();

		initListView();
		
		initRandomView();
		
	}

	private void initRandomView() {
		randomQuestion = (TextView) findViewById(R.id.randomQuestion);
		loadRandomQuestion();
		
		Button nextButton = (Button) findViewById(R.id.nextButton);
		nextButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				loadRandomQuestion();
			}
		});
						
	}
	
	private void loadRandomQuestion(){
		String[] a = getAreas();
		Random r = new Random(System.currentTimeMillis());
		int randomAreaIndex = r.nextInt(a.length);
		
		String[] questions = getQuestions(randomAreaIndex);
		
		int randomQuestionIndex = r.nextInt(questions.length);
		randomQuestion.setText(questions[randomQuestionIndex]);		
	}
	
	private String[] getQuestions(int areaIndex){
		String[] questions = null;
		switch (areaIndex) {
		// Product Marketing Manager
		case 0:
			questions = getResources().getStringArray(
					R.array.product_marketing_manager);
			break;
		case 1:
			// Product Manager
			questions = getResources().getStringArray(
					R.array.product_manager);
			break;
		case 2:
			// Software Engineer
			questions = getResources().getStringArray(
					R.array.software_engineer);
			break;
		case 3:
			// Software Engineer in Test
			questions = getResources().getStringArray(
					R.array.software_engineer_in_test);
			break;
		case 4:
			// Quantitative Compensation Analyst
			questions = getResources().getStringArray(
					R.array.quantitative_compensation_analyst);
			break;
		case 5:
			// Engineering Manager
			questions = getResources().getStringArray(
					R.array.engineering_manager);
			break;
		case 6:
			// AdWords Associate
			questions = getResources().getStringArray(
					R.array.adWords_associate);

			break;
		}
		return questions;
	}

	private String[] getAreas() {
		if(areas == null){
			areas = getResources().getStringArray(R.array.areas);
		}
		return areas;
	}
	
	private void initListView() {
		// List view flipper
		listFlipper = (ViewFlipper) findViewById(R.id.listFlipper);
		// Questions list
		questionsList = (ListView) findViewById(R.id.questionsList);

		// Areas list
		areasList = (ListView) findViewById(R.id.areasList);
		areasList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int pos, long arg3) {
				String[] questions = getQuestions(pos);

				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getApplicationContext(),
						android.R.layout.simple_list_item_1, questions);
				questionsList.setAdapter(adapter);

				listFlipper.showNext();
			}
		});
	}

	private void initTabsHost() {
		tabHost = (TabHost) findViewById(android.R.id.tabhost);

		LocalActivityManager mLocalActivityManager = new LocalActivityManager(
				this, false);
		tabHost.setup(mLocalActivityManager);

		TabHost.TabSpec spec = tabHost.newTabSpec(LIST_TAB);

		spec.setContent(R.id.listTab);
		spec.setIndicator("List");
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec(RANDOM_TAB);

		spec.setContent(R.id.randomTab);
		spec.setIndicator("Random");
		tabHost.addTab(spec);
		
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				if (LIST_TAB.equals(tabId)){
					listFlipper.setDisplayedChild(0);
				}
			}
		});
	}

	@Override
	public void onBackPressed() {
		if (questionsList == listFlipper.getCurrentView()) {
			listFlipper.showPrevious();
		} else {
			super.onBackPressed();
		}
	}
}