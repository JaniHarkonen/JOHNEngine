## TODO
- handle scrolling in Input
???? get information about the monitor from window
- AGame may not need getters for its components
	-- these should be removed if possible as it ruins the modularity of 
	having opt-in components
- add disposing/destruction methods to all relevant classes
- RFullscreen doesn't work yet
	-> doesn't work because windowed fullscreen requires rebuilding the window
	-> rebuilding the window requires first destroying the initial window
	-> when a window is destroyed, the context of that window is also destroyed
	-> in order for OpenGL to work in the new window, its context must be made
	current
	-> this will transfer most of the OpenGL objects to the new context, with the
	exception of state objects (VAOs and FBOs)
	-> VAOs and FBOs have to be regenerated, otherwise nothing will get rendered
- consider if, instead of using populators, submission strategies were used for
IRenderContexts so that they too can extend IRenderable
	-> perhaps eliminate IRenderContext in favor of IRenderable
	-> also eliminates the need for IRenderBufferPopulators as the submission 
	strategies will essentially do their work
- remove Action-enum and switch to using strings
	-> better compatibility with GUI
- consider if GUI could be written using AWorldObjects as they contain many of the
same functionalities (inheritance of position and scaling) as AGUIElements
	-> this would be a better fit as GUI is a type of IWorld and subsequently
	its elements should be AWorldObjects
- the model matrices for GUI-elements are currently being generated on the fly
while being submitted to the render pass
	-> see if the matrix can be calculated seldom and cached
- the way that Mesh and Texture assests are implemented continue to cause issues
	-> currently hotfixed but needs improvement
	-> crashes if the asset loading is skipped (should display default asset instead)
	-> particular issues with how Meshes are being used (too many layers of 
	encapsulation)
- currently CascadeShadowSubRenderer is dependent on the RenderBuffer of the 'cachedvao'
cached vao strategy
	-> once instanced rendering is implemented consider if cachedvao is even necessary
	-> at any rate the two must be separated OR the sub renderer must be a part of the
	'cachedvao' package
	-> consider creating a ISubRenderer interface for all the subrenderers
- update all instances of "cascade" or "cascading" shadow to "cascaded" shadow
- update rest of the light calculations in default.frag to use 'ioViewPosition'

- could compute shaders be used to calculate collisions?
