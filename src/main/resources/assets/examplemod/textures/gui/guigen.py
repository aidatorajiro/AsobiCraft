from PIL import Image, ImageDraw
import os
import numpy as np

ASSET_DIR = os.path.dirname(os.path.abspath(__file__))

def draw_window(im, size, back_color, border_color, border_width):
  x = size[0] - 1
  y = size[1] - 1
  draw = ImageDraw.Draw(im)
  draw.rectangle((0, 0, x, y), fill=back_color)
  if border_width > 0:
    x -= border_width / 2
    y -= border_width / 2
    draw.line((0, 0, 0, y), fill=border_color, width=border_width)
    draw.line((0, y, x+1, y), fill=border_color, width=border_width)
    draw.line((0, 0, x, 0), fill=border_color, width=border_width)
    draw.line((x, 0, x, y+1), fill=border_color, width=border_width)

def draw_slots(im, slot_im, offset, shape):
  for i in range(0, shape[0]):
    for j in range(0, shape[1]):
      x = offset[0] + i * 18
      y = offset[1] + j * 18
      im.paste(slot_im, (x, y), slot_im)

def draw_player_inv(im, slot_im, offset):
  draw_slots(im, slot_im, offset, (9, 3))
  draw_slots(im, slot_im, (offset[0], offset[1] + 58), (9, 1))

# Replace all pixels with given color except pixels with alpha == 0
def replace_all_color(im, color):
  data = np.array(im)
  red, green, blue, alpha = data.T
  data[(alpha!=0).T] = color
  im2 = Image.fromarray(data)
  return im2

SLOT = Image.open(ASSET_DIR + '/slot.png')
PINK_SLOT = replace_all_color(SLOT, (233,182,234,255))
ORANGE_SLOT = replace_all_color(SLOT, (244,200,164,255))
GRAY_SLOT = replace_all_color(SLOT, (204,204,204,255))

LIGHT_BLUE_SLOT = replace_all_color(SLOT, (147,175,175,255))