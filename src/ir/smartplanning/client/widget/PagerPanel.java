package ir.smartplanning.client.widget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.cellview.client.AbstractPager;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.Resources;
import com.google.gwt.user.cellview.client.SimplePager.Style;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.view.client.HasRows;
import com.google.gwt.view.client.Range;

public class PagerPanel  extends AbstractPager {

	int questionCount=-1;
	int startQuestionNo=-1;
	int endQuestionNo=-1;
//	private EventBus eventBus;
	/**
	 * A ClientBundle that provides images for this widget.
	 */
	/**
	 * The location of the text relative to the paging buttons.
	 */
	public static enum TextLocation {
		CENTER, LEFT, RIGHT;
	}

	/**
	 * An {@link Image} that acts as a button.
	 */
	private static class ImageButton extends Image {
		private boolean disabled;
		private final ImageResource resDisabled;
		private final ImageResource resEnabled;
		private final String styleDisabled;

		public ImageButton(ImageResource resEnabled, ImageResource resDiabled, String disabledStyle) {
			super(resEnabled);
			this.resEnabled = resEnabled;
			this.resDisabled = resDiabled;
			this.styleDisabled = disabledStyle;
		}

		public boolean isDisabled() {
			return disabled;
		}

		@Override
		public void onBrowserEvent(Event event) {
			// Ignore events if disabled.
			if (disabled) {
				return;
			}

			super.onBrowserEvent(event);
		}

		public void setDisabled(boolean isDisabled) {
			if (this.disabled == isDisabled) {
				return;
			}

			this.disabled = isDisabled;
			if (disabled) {
				setResource(resDisabled);
				getElement().getParentElement().addClassName(styleDisabled);
			} else {
				setResource(resEnabled);
				getElement().getParentElement().removeClassName(styleDisabled);
			}
		}
	}

	private static int DEFAULT_FAST_FORWARD_ROWS = 1000;
	private static Resources DEFAULT_RESOURCES;

	private static Resources getDefaultResources() {
		if (DEFAULT_RESOURCES == null) {
			DEFAULT_RESOURCES = GWT.create(Resources.class);
		}
		return DEFAULT_RESOURCES;
	}

	private final ImageButton fastForward;

	private final int fastForwardRows;

	private final ImageButton firstPage;

	/**
	 * We use an {@link HTML} so we can embed the loading image.
	 */
	private final HTML label = new HTML();

	private final ImageButton lastPage;
	private final ImageButton nextPage;
	private final ImageButton prevPage;
	
	/**
	 * The {@link Resources} used by this widget.
	 */
	private final Resources resources;

	/**
	 * The {@link Style} used by this widget.
	 */
	private final Style style;

	/**
	 * Construct a {@link SimplePager} with the default text location.
	 */
	String itemText;
	String itemsText;

	public PagerPanel(String itemsText, String itemText) {
		this(TextLocation.CENTER);
		this.itemsText = itemsText;
		this.itemText = itemText;
		

	}
	
	public PagerPanel(String itemsText, String itemText,String detailStyle) {
		this(TextLocation.CENTER);
		this.itemsText = itemsText;
		this.itemText = itemText;
		label.getElement().getParentElement().addClassName(detailStyle);

	}
	
	public String getDetail() {
		return label.getText();
	}

	/**
	 * Construct a {@link SimplePager} with the specified text location.
	 * 
	 * @param location
	 *            the location of the text relative to the buttons
	 */
	@UiConstructor
	// Hack for Google I/O demo
	public PagerPanel(TextLocation location) {
		this(location, getDefaultResources(), true, DEFAULT_FAST_FORWARD_ROWS, false);
	}

	/**
	 * Construct a {@link SimplePager} with the specified resources.
	 * 
	 * @param location
	 *            the location of the text relative to the buttons
	 * @param resources
	 *            the {@link Resources} to use
	 * @param showFastForwardButton
	 *            if true, show a fast-forward button that advances by a larger
	 *            increment than a single page
	 * @param fastForwardRows
	 *            the number of rows to jump when fast forwarding
	 * @param showLastPageButton
	 *            if true, show a button to go the the last page
	 */
	public PagerPanel(TextLocation location, Resources resources, boolean showFastForwardButton, final int fastForwardRows, boolean showLastPageButton) {
		this.resources = resources;
		this.fastForwardRows = fastForwardRows;
		this.style = resources.simplePagerStyle();
		this.style.ensureInjected();

		// Create the buttons.
		String disabledStyle = style.disabledButton();
		firstPage = new ImageButton(resources.simplePagerFirstPage(), resources.simplePagerFirstPageDisabled(), disabledStyle);
		firstPage.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				firstPage();
			}
		});
		nextPage = new ImageButton(resources.simplePagerNextPage(), resources.simplePagerNextPageDisabled(), disabledStyle);
		nextPage.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				nextPage();
			}
		});
		prevPage = new ImageButton(resources.simplePagerPreviousPage(), resources.simplePagerPreviousPageDisabled(), disabledStyle);
		prevPage.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				previousPage();
			}
		});
		if (showLastPageButton) {
			lastPage = new ImageButton(resources.simplePagerLastPage(), resources.simplePagerLastPageDisabled(), disabledStyle);
			lastPage.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					lastPage();
				}
			});
		} else {
			lastPage = null;
		}

		fastForward = null;

		HorizontalPanel layout = new HorizontalPanel();
		layout.setHorizontalAlignment(HorizontalAlignmentConstant.endOf(Direction.RTL));
		layout.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		initWidget(layout);
		if (location == TextLocation.RIGHT) {
			layout.add(label);
		}
		layout.add(firstPage);
		layout.add(prevPage);
		if (location == TextLocation.CENTER) {
			layout.add(label);
		}
		layout.add(nextPage);
		// if (showFastForwardButton) {
		// layout.add(fastForward);
		// }
		if (showLastPageButton) {
			layout.add(lastPage);
		}
		if (location == TextLocation.LEFT) {
			layout.add(label);
		}

		// Add style names to the cells.
		firstPage.getElement().getParentElement().addClassName(style.button());
		prevPage.getElement().getParentElement().addClassName(style.button());
		label.getElement().getParentElement().addClassName(style.pageDetails());
	
		nextPage.getElement().getParentElement().addClassName(style.button());
		// if (showFastForwardButton) {
		// fastForward.getElement().getParentElement().addClassName(style.button());
		// }
		if (showLastPageButton) {
			lastPage.getElement().getParentElement().addClassName(style.button());
		}

		// Disable the buttons by default.
		setDisplay(null);
	}

	@Override
	public void firstPage() {
		super.firstPage();
	}

	@Override
	public int getPage() {
		return super.getPage();
	}

	@Override
	public int getPageCount() {
		return super.getPageCount();
	}

	@Override
	public boolean hasNextPage() {
		return super.hasNextPage();
	}

	@Override
	public boolean hasNextPages(int pages) {
		return super.hasNextPages(pages);
	}

	@Override
	public boolean hasPage(int index) {
		return super.hasPage(index);
	}

	@Override
	public boolean hasPreviousPage() {
		return super.hasPreviousPage();
	}

	@Override
	public boolean hasPreviousPages(int pages) {
		return super.hasPreviousPages(pages);
	}

	@Override
	public void lastPage() {
		super.lastPage();
	}

	@Override
	public void lastPageStart() {
		super.lastPageStart();
	}
	
	public void setQuestionCount(int questionCount) {
		this.questionCount = questionCount;
	}
	public void setStartQuestionNo(int startQuestionNo) {
		this.startQuestionNo = startQuestionNo;
	}
	public void setEndQuestionNo(int endQuestionNo) {
		this.endQuestionNo = endQuestionNo;
	}
	

	@Override
	public void nextPage() {
		if (getDisplay() != null) {
			Range range = getDisplay().getVisibleRange();
			int rowCount = getDisplay().getRowCount();
			// CommonLogger.log(logger, Level.WARNING, "range : [" +
			// range.getStart() + ", " + range.getLength() + "], rowCount : " +
			// rowCount);
			if (rowCount > range.getStart()) {
				setPageStart(range.getStart() + range.getLength());
			}
		}
	}

	@Override
	public void previousPage() {
		super.previousPage();
	}

	@Override
	public void setDisplay(HasRows display) {
		// Enable or disable all buttons.
		boolean disableButtons = (display == null);
		setFastForwardDisabled(disableButtons);
		setNextPageButtonsDisabled(disableButtons);
		setPrevPageButtonsDisabled(disableButtons);
		super.setDisplay(display);
	}

	@Override
	public void setPage(int index) {
		super.setPage(index);
	}

	@Override
	public void setPageSize(int pageSize) {
		super.setPageSize(pageSize);
	}

	@Override
	public void setPageStart(int index) {
		if (super.getDisplay() != null) {
			Range range = super.getDisplay().getVisibleRange();
			int pageSize = range.getLength();
			index = Math.max(0, index);
			if (index != range.getStart()) {
//				int index2 = Math.min(index, super.getDisplay().getRowCount() - pageSize);
//				int size = pageSize;
//				if (index2 < index) {
//					size = pageSize - (index - index2);
//				}
				super.getDisplay().setVisibleRange(index, pageSize);
			}
		}
	}

	/**
	 * Let the page know that the table is loading. Call this method to clear
	 * all data from the table and hide the current range when new data is being
	 * loaded into the table.
	 */
	public void startLoading() {
		getDisplay().setRowCount(0, false);
		label.setHTML("");
	}

	/**
	 * Get the text to display in the pager that reflects the state of the
	 * pager.
	 * 
	 * @return the text
	 */
	protected String createText() {

		// Default text is 1 based.
		NumberFormat formatter = NumberFormat.getFormat("#,###");
		HasRows display = getDisplay();
		if (display.getRowCount() == 0) {
			return itemText + "  موجود نیست.";
		}
		Range range = display.getVisibleRange();
		int pageStart = range.getStart() + 1;
		int pageSize = range.getLength();
		int dataSize = display.getRowCount();
		int endIndex = Math.min(dataSize, pageStart + pageSize - 1);
		endIndex = Math.max(pageStart, endIndex);
		boolean exact = display.isRowCountExact();
		if (questionCount==-1){
		return itemsText + " " + formatter.format(pageStart) + "تا" + formatter.format(endIndex) + (exact ? " از" : " بیش از") + formatter.format(dataSize)
				+ itemText;
		}else{
			return itemsText + " " + formatter.format(startQuestionNo) + "تا" + formatter.format(endQuestionNo) + (exact ? " از" : " بیش از") + formatter.format(questionCount)
					+ itemText;
		}
	}
	public void refreshPagerInfo(){
		label.setText(createText());
	}
	@SuppressWarnings("deprecation")
	@Override
	protected void onRangeOrRowCountChanged() {
		HasRows display = getDisplay();
		label.setDirection(Direction.RTL);
		label.setText(createText());
		setPrevPageButtonsDisabled(!hasPreviousPage());
		if (isRangeLimited() || !display.isRowCountExact()) {
			setNextPageButtonsDisabled(!hasNextPage());
			setFastForwardDisabled(!hasNextPages(getFastForwardPages()));
		}
		if (display.getRowCount() == 0) {
			setNextPageButtonsDisabled(true);
			createText();
		}
	}

	/**
	 * Check if the next button is disabled. Visible for testing.
	 */
	boolean isNextButtonDisabled() {
		return nextPage.isDisabled();
	}

	/**
	 * Check if the previous button is disabled. Visible for testing.
	 */
	boolean isPreviousButtonDisabled() {
		return prevPage.isDisabled();
	}

	/**
	 * Get the number of pages to fast forward based on the current page size.
	 * 
	 * @return the number of pages to fast forward
	 */
	private int getFastForwardPages() {
		int pageSize = getPageSize();
		return pageSize > 0 ? fastForwardRows / pageSize : 0;
	}

	/**
	 * Enable or disable the fast forward button.
	 * 
	 * @param disabled
	 *            true to disable, false to enable
	 */
	private void setFastForwardDisabled(boolean disabled) {
		if (fastForward == null) {
			return;
		}
		if (disabled) {
			fastForward.setResource(resources.simplePagerFastForwardDisabled());
			fastForward.getElement().getParentElement().addClassName(style.disabledButton());
		} else {
			fastForward.setResource(resources.simplePagerFastForward());
			fastForward.getElement().getParentElement().removeClassName(style.disabledButton());
		}
	}

	/**
	 * Enable or disable the next page buttons.
	 * 
	 * @param disabled
	 *            true to disable, false to enable
	 */
	private void setNextPageButtonsDisabled(boolean disabled) {
		nextPage.setDisabled(disabled);
		if (lastPage != null) {
			lastPage.setDisabled(disabled);
		}
	}

	/**
	 * Enable or disable the previous page buttons.
	 * 
	 * @param disabled
	 *            true to disable, false to enable
	 */
	private void setPrevPageButtonsDisabled(boolean disabled) {
		firstPage.setDisabled(disabled);
		prevPage.setDisabled(disabled);
	}

}
