### Developer's goal
Create RecyclerView that has possibility to expand/collapse its items smoothly.
While we don't know height of the expanded item views beforehand.

![Demo GIF](https://github.com/andriipanasiuk/ExpandableRecyclerView/blob/master/demo.gif)

### Integration into the project

For now you can use it as library project with the code or simply copy all the needed staff (package `core`) into your project.

### Usage

In package `sample` there is the example of `ExpandableRecyclerView` usage.
`Core` package contains all the library's staff.

### Under the hood

In fact we have `FrameLayout` with 2 childs:
 - `ScrollView` containing view same as RecyclerView's item
 - `RecyclerView` itself.

The first one is invisible and used for measuring height of the expanded state of the RecyclerView's item. 
We go such way cause before animating item we have to know its desired height after expanding.
This view placed inside the `ScrollView` so that we could have items bigger than screen height.

