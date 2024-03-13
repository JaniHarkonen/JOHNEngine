## TODO
- handle scrolling in Input
???? get information about the monitor from window
- remove cringe
???? new exception class??
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
- OpenGL crashes when the window is closed because the renderer still continues
to render (using OpenGL-commands) after window.stop() has been called
	-> GLFWterminate() is called upon window.stop()
- change name of RenderBufferStrategy to RenderBuffer and strategoids to strategies
as it is becoming clearer that RenderBufferStrategy is going to contain a snapshot
of the game world
- ARequest should be IRequest due to no internal state
- cursor should not lock to center as this causes weird issues when calculating 
mouse delta on low frame rates
	-> use OpenGL's disable cursor
- RFullscreen doesn't work yet

- add default material
- see if Material can be refactored into binding all its textures through a method
- Input classes should probably convert mouse coordinates into floats instead of 
deferring this to the controllers
- reconsider the way that Input is being handled
	x. is AInput needed, what about IInput (probably the ladder)
	2. how would gamepad controllers be implemented?
	3. what changes as opposed to GLFW mouse and keyboard input?
	4. can this be generalized to the way that the input is being handled currently?
- there are two IRenderBufferStrategy classes?
- could compute shaders be used to calculate collisions?
- THERE ARE READ-ONLY VECTORS (Vector3fc, Quaternionfc)
	-- see if these can be implemented throughout the codebase
	-- don't forget Quaternions
- instead of having MouseKeyboardInputGL, have InputGL which handles all OpenGL-
based input
- KeyDown should be Key instead so that key release can be handled with the same
InputEvent by setting intensity to 0.0f
- have a single final class where global declarations are made
	-- perhaps split the class up into different classes
- consider changing RenderStrategies to RenderPasses
- move IRenderBufferStrategy to core as it is no longer dependent on JWorld
- reconsider the whole idea of having the RequestManager in 'core'-package
	-- inheritance should be removed as there's no reason for polymorphism
	-- different types of RequestManagers should not share functionality via
	inheritance
- reconsider the idea of RenderUnits
	-- at least RenderUnits shouldn't have private fields, rather, their fields 
	should be package private
- rename 'cells' to 'glyphs' in Font
