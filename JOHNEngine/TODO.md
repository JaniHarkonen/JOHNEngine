## TODO
- handle scrolling in Input
???? get information about the monitor from window
??? should ABasicWindowRequests be implemented using lambdas instead???
- remove cringe
???? new exception class??
- consider what renderer functionalities can be defined in ARenderer
- AGame may not need getters for its components
	-- these should be removed if possible as it ruins the modularity of 
	having opt-in components
- maybe AssetManager should also hold a map of AAssetLoaders
- add disposing/destruction methods to all relevant classes
- in order to do indirect rendering do this:
	-- each rendered instance must produce an entry in a map before being 
	rendered
		o the map will pair meshes with maps of textures
		o maps of textures will pair textures with lists of "orientations"
		that combine rotation and position vectors
	-- the renderer will go through the mesh-texture-orientation data structures
	iteratively and render them
	-- ARenderAssets should have bind()- and generate()-methods instead of a
	render()-method (they should not implement IDrawable)
- consider if RCursorLockToCenter is needed
- OpenGL crashes when the window is closed because the renderer still continues
to render (using OpenGL-commands) after window.stop() has been called
	-> GLFWterminate() is called upon window.stop()
- change name of RenderBufferStrategy to RenderBuffer and strategoids to strategies
as it is becoming clearer that RenderBufferStrategy is going to contain a snapshot
of the game world
- ARequest should be IRequest due to no internal state

- add default material
- all IGeometryComponents should be able to inherit their base values from parents
- try to minimize the number of changed() calls in CTransform to avoid redundancy