## TODO
- handle scrolling in Input
???? get information about the monitor from window
- AGame may not need getters for its components
	-- these should be removed if possible as it ruins the modularity of 
	having opt-in components
- add disposing/destruction methods to all relevant classes
- change name of RenderBufferStrategy to RenderBuffer and strategoids to strategies
as it is becoming clearer that RenderBufferStrategy is going to contain a snapshot
of the game world
- cursor should not lock to center as this causes weird issues when calculating 
mouse delta on low frame rates
	-> use OpenGL's disable cursor
- RFullscreen doesn't work yet
	-> doesn't work because windowed fullscreen requires rebuilding the window
	-> rebuilding the window requires first destroying the initial window
	-> when a window is destroyed, the context of that window is also destroyed
	-> in order for OpenGL to work in the new window, its context must be made
	current
	-> this will transfer most of the OpenGL objects to the new context, with the
	exception of state objects (VAOs and FBOs)
	-> VAOs and FBOs have to be regenerated, otherwise nothing will get rendered

- reconsider the way that Input is being handled
	x. is AInput needed, what about IInput (probably the ladder)
	2. how would gamepad controllers be implemented?
	3. what changes as opposed to GLFW mouse and keyboard input?
	4. can this be generalized to the way that the input is being handled currently?
- could compute shaders be used to calculate collisions?
- consider changing RenderStrategies to RenderPasses
- reconsider the idea of RenderUnits
	-- at least RenderUnits shouldn't have private fields, rather, their fields 
	should be package private
- specify different glyph layouts in Font so that the glyphs can either be placed
in symmetric cells or in cells of differing sizes
	-- GridLayout, where the font texture is divided using a grid where each cell
	is of equal size
	-- TileLayout, where each glyph occupies its own tile whose coordinates and
	dimensions are recorded in a separate table
