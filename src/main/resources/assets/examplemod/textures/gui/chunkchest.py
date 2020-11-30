from guigen import *
from PIL import *
import math

im = Image.new("RGBA", (256, 256), (255, 255, 255, 0))
draw_window(im, (178, 234), (80, 80, 80, 255), (150, 80, 80, 255), 2)

draw_slots(im, GRAY_SLOT, (9, 16), (9, 3))
draw_player_inv(im, GRAY_SLOT, (9, 151))
im.paste(GRAY_SLOT, (135, 106), GRAY_SLOT)

expand = Image.open(ASSET_DIR + '/expand.png')
im.paste(expand, (45, 106), expand)

centerX = 89
centerY = 76
radius = 60
theta = 2*math.pi/16.0

im.save(ASSET_DIR + "/chunkchest.png")
