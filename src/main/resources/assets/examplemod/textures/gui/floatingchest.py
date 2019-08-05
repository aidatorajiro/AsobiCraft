from guigen import *
from PIL import *
import math

im = Image.new("RGBA", (256, 256), (255, 255, 255, 0))
draw_window(im, (178, 234), (169, 200, 201, 255), (249, 167, 79, 255), 2)

draw_player_inv(im, LIGHT_BLUE_SLOT, (9, 151))

centerX = 89
centerY = 76
radius = 60
theta = 2*math.pi/16.0

im.save(ASSET_DIR + "/floatingchest.png")
