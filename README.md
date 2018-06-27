# README
## Naming
* ### Layouts
> **<WHAT>_<WHERE>.xml** - activity_home.xml / fragment_chats.xml / etc.
* ### Strings
> The **<WHAT>** part for Strings is irrelevant. So either we use <WHERE> to indicate where the string will be used
>
> **<WHERE>_<DESCRIPTION>** - chat_title / home_section_description / etc.
>
> or
>
> **<ALL>_<DESCRIPTION>** - all_done / string_all_close / etc.
* ### Drawables
> **<WHAT>_<WHERE>_<DESCRIPTION>_?<SIZE>** - background_splash / selector_search_button / etc.
* ### Drawable icons
> **<WHAT>_<DESCRIPTION>_?<SIZE>** - icon_movies / icon_share / icon_point_48 / etc.
* ### Ids
> **<WHERE>_<DESCRIPTION>_<WHAT>** - home_title_text_view / chat_avatar_image_view / main_tab_layout
* ### Dimensions
> **<FOR WHAT>_<WHERE>_<DESCRIPTION>_<WHAT>** - icon_home_menu_size / list_single_line_content_spacing
* ### View
> **<DESCRIPTION>_<WHAT>** - titleView / skipButton 
>
> View          | 	Name
> ------------- | -------------
> TextView      | titleView
> Button        | skipButton
> ImageView     | avatarView
> RecyclerViiew | moviesRecyclerViiew
> RelativeLayout| rootLayout
* ### Dimension
>
> Prefix        | 	Usage
> ------------- | -------------
> width         | width in dp
> height        | height in dp
> size          | if width == height
> spacing       | for margin and padding

## Code Style
```java
// bad
String lang = config.getLanguage();
if (lang == null) {
    lang = DEFAULT_LANG;
}
return baseUrl + BASE_URL_PR + countryCode + SLASH + lang + BASE_URL_END;


// good
String lang = config.getOESPLanguage();

if (lang == null || StringUtil.isEmpty(lang)) {
    lang = DEFAULT_LANG;
}

return baseUrl + BASE_URL_PR + countryCode + SLASH + lang + BASE_URL_END;
```
```java
// bad
final int len = views.size();
for (int i = 0; i < len; i++) {
    // code
}
canvas.restore();

// good
final int len = views.size();

for (int i = 0; i < len; i++) {
    // code
}

canvas.restore();
```
Put empty lines before and after **if** / **for** / **while** / **switch** / **try** blocks if they in middle of the code

```java
// bad
if (v.getBottom() >= getBottom()) {
    adapter.notifyGroupExpanded(groupPos);
    return expandGroup(groupPos);
}

// good
if (v.getBottom() >= getBottom()) {
    adapter.notifyGroupExpanded(groupPos);

    return expandGroup(groupPos);
}
```
```java
// bad
public boolean collapseGroupWithAnimation(int groupPos) {
    int groupFlatPos = getFlatListPosition(getPackedPositionForGroup(groupPos));
	// code
    adapter.notifyDataSetChanged();
    return isGroupExpanded(groupPos);
}

// good
public boolean collapseGroupWithAnimation(int groupPos) {
    int groupFlatPos = getFlatListPosition(getPackedPositionForGroup(groupPos));
	// code
    adapter.notifyDataSetChanged();

    return isGroupExpanded(groupPos);
}
```
```java
// bad
public boolean collapseGroupWithAnimation(int groupPos) {

    return isGroupExpanded(groupPos);
}

// good
public boolean collapseGroupWithAnimation(int groupPos) {
    return isGroupExpanded(groupPos);
}
```
```java
// bad
for (int i = info.firstChildPosition; i < len; i++) {
    if (totalHeight < clipHeight) {
        // code
    } else {
        dummyView.addFakeView(childView);
        int averageHeight = totalHeight / (i + 1);
        totalHeight += (len - i - 1) * averageHeight;
        break;
    }
}

// good
for (int i = info.firstChildPosition; i < len; i++) {
    if (totalHeight < clipHeight) {
        // code
    } else {
        dummyView.addFakeView(childView);
        int averageHeight = totalHeight / (i + 1);
        totalHeight += (len - i - 1) * averageHeight;

        break;
    }
}
```
```java
// bad
switch (month) {
    case DECEMBER:
        numDays = 31;

        break;
    case NOVEMBER:
        numDays = 30;
		counter++;
        break;
    default:
        System.out.println("Invalid month.");
        break;
}
```
```java
// good
switch (month) {
    case DECEMBER:
        numDays = 31;

		break;
    case NOVEMBER:
        numDays = 30;
		counter++;

        break;
    default:
        System.out.println("Invalid month.");

		break;
}

@Override
protected void onPause() {
    super.onPause();

	//code
}

@Override
protected void onPause() {
	//code

    super.onPause();

	//code
}
```