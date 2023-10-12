import {useState} from 'react';
import {ColorPicker, Badge, Text, Box, DEFAULT_THEME} from '@mantine/core';

interface SColorPickerProps {
  value: string,
  label: string,
  onChange: (value: string) => void;
}

const SColorPicker = ({value, label, onChange}: SColorPickerProps) => {

  const [currentColor, setCurrentColor] = useState('#dee2e6');

  const handleChange = (event:string) => {
    setCurrentColor(event);
    onChange(event)
  }

  return (
      <div className="my-8">
        <div className="flex flex-row">
          <Text className=" mr-3">{label}</Text>
          <Badge style={{backgroundColor: currentColor, color: 'black'}}>
            {currentColor}
          </Badge>
        </div>

        <Box maw={300}>
          <ColorPicker
              format="hex"
              value={currentColor}
              onChange={event => handleChange(event)}
              withPicker={false}
              fullWidth
              swatches={[
                ...DEFAULT_THEME.colors.gray.slice(3, 4),
                ...DEFAULT_THEME.colors.gray.slice(7, 8),
                ...DEFAULT_THEME.colors.red.slice(3, 4),
                ...DEFAULT_THEME.colors.red.slice(7, 8),
                ...DEFAULT_THEME.colors.grape.slice(3, 4),
                ...DEFAULT_THEME.colors.grape.slice(7, 8),
                ...DEFAULT_THEME.colors.violet.slice(3, 4),
                ...DEFAULT_THEME.colors.violet.slice(7, 8),
                ...DEFAULT_THEME.colors.blue.slice(3, 4),
                ...DEFAULT_THEME.colors.blue.slice(7, 8),
                ...DEFAULT_THEME.colors.cyan.slice(3, 4),
                ...DEFAULT_THEME.colors.cyan.slice(7, 8),
                ...DEFAULT_THEME.colors.teal.slice(3, 4),
                ...DEFAULT_THEME.colors.teal.slice(7, 8),
                ...DEFAULT_THEME.colors.green.slice(3, 4),
                ...DEFAULT_THEME.colors.green.slice(7, 8),
                ...DEFAULT_THEME.colors.yellow.slice(3, 4),
                ...DEFAULT_THEME.colors.yellow.slice(7, 8),
                ...DEFAULT_THEME.colors.orange.slice(3, 4),
                ...DEFAULT_THEME.colors.orange.slice(7, 8),
              ]}
          />
        </Box>
      </div>
  )
}

export default SColorPicker;