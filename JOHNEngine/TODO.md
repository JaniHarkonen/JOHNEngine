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
- instead of using custom exceptions, create a logger-class that can log into
different targets, such as the console or a text file
- remove Action-enum and switch to using strings
	-> better compatibility with GUI
- consider if GUI could be written using AWorldObjects as they contain many of the
same functionalities (inheritance of position and scaling) as AGUIElements
	-> this would be a better fit as GUI is a type of IWorld and subsequently
	its elements should be AWorldObjects
- the model matrices for GUI-elements are currently being generated on the fly
while being submitted to the render pass
	-> see if the matrix can be calculated seldom and cached
- add comments to the GUI markup language
- add x and y properties to GUI markup language

- could compute shaders be used to calculate collisions?
