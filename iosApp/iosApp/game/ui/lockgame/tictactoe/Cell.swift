//
//  Cell.swift
//  iosApp
//
//  Created by Hao Wu on 9/30/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct Cell
{
    var tile: Tile
    
    func displayTile() -> String
    {
        switch(tile)
        {
            case Tile.Nought:
                    return "O"
            case Tile.Cross:
                return "X"
            default:
                return ""
        }
    }
    
    func tileColor() -> Color
    {
        switch(tile)
        {
            case Tile.Nought:
                return Color.gray
            case Tile.Cross:
                return Color.pink
            default:
                return Color.black
        }
    }
}

enum Tile
{
    case Nought
    case Cross
    case Empty
}
