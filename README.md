# News App


#### Overview
The goal is to create a News feed app which gives a user regularly-updated news from the internet related to a particular topic, person, or location. The project is part of Nanodegree program on [Udacity](https://www.udacity.com/).

#### Helpers
- Guardian API
- JSON Parsing
- HTTP Networking
- Threads & Parallelism
- Fetching data from an API
- Using an AsyncTask
- Loader Manager
- Picasso library
- Shared Preference
- Preference Fragment
- Web Intent
- Drawer layout
- Uri builder

#### API
In this project, I am using [Guardian API](https://open-platform.theguardian.com/documentation/). It is well-maintained and returns information in a JSON format.

```
http://content.guardianapis.com/search?q=debate&tag=politics/politics&from-date=2014-01-01&api-key=test
http://content.guardianapis.com/search?q=debates&api-key=test 
```
#### Picasso Library
Images add much-needed context and visual flair to Android applications. Picasso allows for hassle-free image loading in your application—often in one line of code!

- Handling `ImageView` recycling and download cancelation in an adapter.
- Complex image transformations with minimal memory use.
- Automatic memory and disk caching.

```
 Picasso.get()
	.load(getString(R.string.your_link))
	.into(imageView);
```

###### Gradle dependencies
```
implementation 'com.squareup.picasso:picasso:2.71828'
```

###### Adapter Downloads
```
@Override public void getView(int position, View convertView, ViewGroup parent) {
  SquaredImageView view = (SquaredImageView) convertView;
  if (view == null) {
    view = new SquaredImageView(context);
  }
  String url = getItem(position);
  Picasso.get().load(url).into(view);
}
```

The source code to the Picasso, its samples, and this website is [available on Github](https://github.com/square/picasso).

#### UX DESIGN

<img src="https://raw.githubusercontent.com/cvbutani/NewsApp/master/UXDesign/ux1.png" width="200"/> <img src="https://raw.githubusercontent.com/cvbutani/NewsApp/master/UXDesign/ux2.png" width="200"/> <img src="https://raw.githubusercontent.com/cvbutani/NewsApp/master/UXDesign/ux3.png" width="200"/>

<img src="https://raw.githubusercontent.com/cvbutani/NewsApp/master/UXDesign/ux4.png" width="200"/> <img src="https://raw.githubusercontent.com/cvbutani/NewsApp/master/UXDesign/ux5.png" width="200"/> <img src="https://raw.githubusercontent.com/cvbutani/NewsApp/master/UXDesign/ux6.png" width="200"/>
