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
- change naming of "topLevel..." in GUI RendererContext to "container..."

- could compute shaders be used to calculate collisions?
